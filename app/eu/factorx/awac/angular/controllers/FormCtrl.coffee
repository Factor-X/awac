angular
.module('app.controllers')
.controller "FormCtrl", ($scope, downloadService, $http, messageFlash, modalService, formIdentifier) ->
    $scope.formIdentifier = formIdentifier

    #this variable contains all answer, new and old
    $scope.answerList = []
    $scope.mapRepetition = []
    ###
    illustration of the structures
    $scope.mapRepetition['A15'] = [{'A15':1},
                                    {'A15':2}]

    $scope.mapRepetition['A16'] = [{'A16':1,'A15':1},
                                   {'A16':2,'A15':1}]
    ###

    $scope.loading = true

    #display the loading modal
    modalService.show(modalService.LOADING)

    downloadService.getJson "answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, (data) ->
        console.log "data"
        console.log data
        $scope.o = angular.copy(data)

        $scope.loopRepetition = (questionSetDTO, listQuestionSetRepetition = []) ->
            if questionSetDTO.repetitionAllowed == true

                listQuestionSetRepetition[listQuestionSetRepetition.length] = questionSetDTO.code

                #find if the answer are already repeated on this repetition
                for answer in $scope.answerList
                    if answer.mapRepetition != null && answer.mapRepetition != undefined
                        if answer.mapRepetition[questionSetDTO.code] != null && answer.mapRepetition[questionSetDTO.code] != undefined && answer.mapRepetition[questionSetDTO.code] != 0
                            $scope.addMapRepetition(questionSetDTO.code, answer.mapRepetition,
                                listQuestionSetRepetition)


            if questionSetDTO.children
                for child in questionSetDTO.children
                    $scope.loopRepetition(child, angular.copy(listQuestionSetRepetition))

        #
        # add default value to answer
        #
        $scope.addDefaultValue = (questionSetDTO) ->
            for question in questionSetDTO.questions
                if question.defaultValue != undefined && question.defaultValue != null
                    for answer in $scope.answerList
                        if answer.questionKey == question.code && answer.value == null
                            answer.value = question.defaultValue
            for child in questionSetDTO.children
                $scope.addDefaultValue(child)


        #build the list of answers
        #recove answerSave
        if $scope.o.answersSave != null && $scope.o.answersSave != undefined
            answerSave = $scope.o.answersSave
            #save answer        i
            $scope.answerList = answerSave.listAnswers

        #build list of repetition for the mmAwacRepetition
        for qSet in $scope.o.questionSets
            $scope.loopRepetition(qSet)

        console.log "$scope.mapRepetition"
        console.log $scope.mapRepetition

        #use the defaultValues to completed null value
        for questionSetDTO in $scope.o.questionSets
            $scope.addDefaultValue(questionSetDTO)


        #hide the loading modal
        modalService.hide(modalService.LOADING)

        $scope.loading = false


    #
    # save the result of this form
    #
    $scope.$on 'SAVE', () ->

        #display the loading modal
        modalService.show(modalService.LOADING)

        #build the list to save
        listAnswerToSave = []
        for answer in $scope.answerList
            if answer.value && (answer.value.$valid == null || answer.value.$valid == undefined || answer.value.$valid == true)
                if answer.wasEdited != undefined
                    delete answer['wasEdited']
                listAnswerToSave[listAnswerToSave.length] = answer

        console.log "listAnswerToSave"
        console.log listAnswerToSave

        #and replace the list
        $scope.o.answersSave.listAnswers = listAnswerToSave


        promise = $http
            method: "POST"
            url: 'answer/save'
            headers:
                "Content-Type": "application/json"
            data: $scope.o.answersSave

        promise.success (data, status, headers, config) ->
            messageFlash.displaySuccess "Your answers are saved !"
            modalService.hide(modalService.LOADING)
            return

        promise.error (data, status, headers, config) ->
            messageFlash.displayError "An error was thrown during the save : " + data.message
            modalService.hide(modalService.LOADING)
            return

    #
    # get list choice by question code
    #
    $scope.getUnitCategories = (code) ->
        if $scope.loading
            return null

        #recover the question
        question = $scope.getQuestion(code)
        #recover the list
        if question == null || question == undefined
            console.log "ERROR : this question was not found : " + code
            return null
        if question.unitCategoryId == null || question.unitCategoryId == undefined
            console.log "ERROR : there is no unitCategoryId for this question : " + code
            return null
        return $scope.o.unitCategories[question.unitCategoryId]


    #
    # get list choice by question code
    #
    $scope.getCodeList = (code) ->
        if $scope.loading
            return null

        #recover the question
        question = $scope.getQuestion(code)
        #recover the list
        return $scope.o.codeLists[question.codeListName]

    #
    # get the repetitionMap by code and mapRepetition
    # use by mm-awac-repetition-question for the ng-repeat
    #
    $scope.getRepetitionMapByQuestionSet = (code, mapRepetition) ->
        listRepetition = []
        if $scope.mapRepetition[code] != null && $scope.mapRepetition[code] != undefined
            for repetition in $scope.mapRepetition[code]

                #control map
                if mapRepetition == null || mapRepetition == undefined || $scope.compareRepetitionMap(repetition, mapRepetition)
                    listRepetition[listRepetition.length] = repetition

        return listRepetition

    #
    # getQuestionByCode
    #
    $scope.getQuestion = (code, listQuestionSets = null) ->
        if listQuestionSets == null
            if  $scope.o == null || $scope.o == undefined || $scope.o.questionSets == null
                return null
            listQuestionSets = $scope.o.questionSets

        if listQuestionSets
            for qSet in listQuestionSets
                if qSet.questions
                    for q in qSet.questions
                        if q.code == code
                            return q
                if qSet.children
                    result = $scope.getQuestion(code, qSet.children)
                    if result
                        return result
        return null


    $scope.getAnswerToCompare = (code, mapIteration) ->
        if $scope.dataToCompare != null
            for answer in $scope.dataToCompare.answersSave.listAnswers
                #control the code
                if answer.questionKey == code

                    #control the repetition map
                    if $scope.compareRepetitionMap(answer.mapRepetition, mapIteration)
                        return answer

        return null

    #
    # get the answer by code and mapIteration
    # if there is not answer for this case, create it
    #
    $scope.getAnswerOrCreate = (code, mapIteration) ->
        if code == null || code == undefined
            console.log "ERROR !! getAnswerOrCreate : code is null or undefined"
            return null


        result = $scope.getAnswer(code, mapIteration)
        if result
            return result
        else
            #compute default value
            value = null
            unitId = null
            if $scope.loading == false
                question = $scope.getQuestion(code)

                #if question.defaultValue != null && question.defaultValue != undefined
                value = question.defaultValue

                #compute default unitId
                if question.unitCategoryId != null && question.unitCategoryId != undefined
                    unitId = $scope.getUnitCategories(code).mainUnitId


            #if the answer was not founded, create it
            answerLine = {
                'questionKey': code
                'value': value
                'unitId': unitId
                'mapRepetition': mapIteration
            }
            $scope.answerList[$scope.answerList.length] = answerLine
            return answerLine

    #
    # getAnswerByQuestionCode and mapIteration
    #
    $scope.getAnswer = (code, mapIteration) ->
        for answer in $scope.answerList
            #control the code
            if answer.questionKey == code

                #control the repetition map
                if $scope.compareRepetitionMap(answer.mapRepetition, mapIteration)
                    return answer

        return null

    #
    # get a list answer by code and mapIteration
    # the response can by a list in function of the mapIteration
    #
    $scope.getListAnswer = (code, mapIteration) ->
        listAnswer = []

        for answer in $scope.answerList
            #control the code
            if answer.questionKey == code && $scope.compareRepetitionMap(answer.mapRepetition, mapIteration)
                listAnswer[listAnswer.length] = answer

        return listAnswer

    #
    # add a iteration for the code and the mapRepetition
    #
    $scope.addIteration = (code, mapRepetition) ->
        max = 0
        repetitionToAdd = {}
        #exemple : {'A273' : 1,'A243':2}

        #if there is already a mapRepetition, used it for the new repetitionToAdd
        if mapRepetition != null && mapRepetition != undefined
            repetitionToAdd = angular.copy(mapRepetition)

        if $scope.mapRepetition[code] == null || $scope.mapRepetition[code] == undefined
            #there is no repetition for the code => create a new iteration
            repetitionToAdd[code] = max + 1
            $scope.mapRepetition[code] = []
            $scope.mapRepetition[code][0] = repetitionToAdd
        else
            for repetition in $scope.mapRepetition[code]
                if $scope.compareRepetitionMap(repetition, mapRepetition) && repetition[code] > max
                    max = repetition[code]

            repetitionToAdd[code] = max + 1
            $scope.mapRepetition[code][$scope.mapRepetition[code].length] = repetitionToAdd

    #
    # remove an iteration by question set code, number of the iteration and the mapRepetition
    #
    $scope.removeIteration = (questionSetCode, iterationToDelete, mapRepetition) ->

        #delete question
        len = $scope.answerList.length
        while (len--)
            question = $scope.answerList[len]
            if question.mapRepetition != null && question.mapRepetition != undefined && $scope.compareRepetitionMap(question.mapRepetition,mapRepetition)
                if question.mapRepetition[questionSetCode] && question.mapRepetition[questionSetCode] == iterationToDelete[questionSetCode]
                    $scope.answerList.splice(len, 1)

        # delete iteration
        # check all iteration because it must remove the iteration linked to the iteration to delete
        for key in Object.keys($scope.mapRepetition)
            if key != '$$hashKey'
                #value = mapContained[key]
                len = $scope.mapRepetition[key].length
                while (len--)
                    iteration = $scope.mapRepetition[key][len]
                    if $scope.compareRepetitionMap(iteration,mapRepetition) && iteration[questionSetCode] && iteration[questionSetCode] == iterationToDelete[questionSetCode]
                        $scope.mapRepetition[key].splice(len, 1)

    #
    # compare to mapRepetition
    # if the mapContained is null or undefined, the result is true
    # if all items of the second are included into the first, return true
    #
    $scope.compareRepetitionMap = (mapContainer, mapContained) ->
        if mapContained == null || mapContained == undefined || mapContained.length == 0
            return true
        if mapContainer == null || mapContainer == undefined || mapContainer.length == 0
            return false
        for key in Object.keys(mapContained)
            if key != '$$hashKey'
                value = mapContained[key]
                if mapContainer[key] == null || mapContainer[key] == undefined || mapContainer[key] != value
                    return false
        return true

    #
    # add a mapRepetition into the variable $scope.mapRepetition
    #
    $scope.addMapRepetition = (questionSetCode, mapRepetitionSource, listQuestionSetRepetition) ->

        #build the map repetition for this questionSet
        mapRepetitionToAdd = {}
        for key in Object.keys(mapRepetitionSource)
            if key != '$$hashKey'
                for questionCode in listQuestionSetRepetition
                    if questionCode == key
                        mapRepetitionToAdd[key] = mapRepetitionSource[key]

        #control if this map is already store and add it if it's needed
        if $scope.mapRepetition[questionSetCode]
            founded = false
            for repetition in $scope.mapRepetition[questionSetCode]
                if $scope.compareRepetitionMap(repetition, mapRepetitionToAdd)
                    founded = true
            if founded == false
                $scope.mapRepetition[questionSetCode][$scope.mapRepetition[questionSetCode].length] = angular.copy(mapRepetitionToAdd)
        else
            $scope.mapRepetition[questionSetCode] = []
            $scope.mapRepetition[questionSetCode][0] = angular.copy(mapRepetitionToAdd)


    $scope.validNavigation = ->
        result = {}
        result.modalForConfirm = modalService.CONFIRMATION_EXIT_FORM

        for answer in $scope.answerList
            if answer.wasEdited != undefined && answer.wasEdited == true
                result.valid = false
                break

        return result

    $scope.dataToCompare = null


    $scope.$parent.$watch 'periodToCompare', () ->

        if $scope.periodToCompare != null
            promise = $http
                method: "GET"
                url: 'answer/getByForm/' + $scope.formIdentifier + "/" + $scope.periodToCompare + "/" + $scope.$parent.scopeId
                headers:
                    "Content-Type": "application/json"
            promise.success (data, status, headers, config) ->
                $scope.dataToCompare = data
                return

            promise.error (data, status, headers, config) ->
                return

