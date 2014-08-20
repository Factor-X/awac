angular
.module('app.controllers')
.controller "FormCtrl", ($scope, downloadService, $http, messageFlash, translationService, modalService, formIdentifier, $timeout,displayFormMenu) ->

    $scope.formIdentifier = formIdentifier
    $scope.displayFormMenu=displayFormMenu

    # store the structure and the status of tabSet and tab
    $scope.tabSet = {}

    # declare the dataToCompare variable. This variable is used to display data to compare
    # the content is a FormDTO object (after loading by the $scope.$parent.$watch function
    $scope.dataToCompare = null

    #this variable contains all answer, new and old
    $scope.answerList = []
    $scope.mapRepetition = {}
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

    #
    # load the form and treat structure and data
    #
    downloadService.getJson "/awac/answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId, (data, status) ->

        if status != 200
            messageFlash.displayError 'Unable to load data...'
            modalService.close(modalService.LOADING)
        else
            $scope.o = angular.copy(data)


            #build the list of answers
            #recove answerSave
            if $scope.o.answersSave != null && $scope.o.answersSave != undefined
                answerSave = $scope.o.answersSave
                #save answer        i
                $scope.answerList = answerSave.listAnswers

            #
            # compute mapRepetition
            #
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
            # add default value to answer and unit
            #
            $scope.addDefaultValue = (questionSetDTO) ->
                for question in questionSetDTO.questions
                    if question.defaultValue != undefined && question.defaultValue != null
                        for answer in $scope.answerList
                            if answer.questionKey == question.code && answer.value == null
                                answer.value = question.defaultValue

                for child in questionSetDTO.children
                    $scope.addDefaultValue(child)

            args = {}

            args.time = $scope.o.answersSave.lastUpdateDate

            $scope.$root.$broadcast("REFRESH_LAST_SAVE_TIME", args)


            #build list of repetition for the mmAwacRepetition
            for qSet in $scope.o.questionSets
                $scope.loopRepetition(qSet)

            #console.log "$scope.mapRepetition"
            #console.log $scope.mapRepetition

            #use the defaultValues to completed null value
            for questionSetDTO in $scope.o.questionSets
                $scope.addDefaultValue(questionSetDTO)

            $timeout(->
                $scope.createTabSet()
                $timeout(->
                    modalService.close(modalService.LOADING)
                    $scope.loading = false
                    $scope.$root.$broadcast('FORM_LOADING_FINISH')
                , 0)
            , 0)

            return


    $scope.$on 'SAVE_AND_NAV', (event, args) ->
        $scope.save(args)

    #
    # save the result of this form
    #
    $scope.$on 'SAVE', () ->
        $scope.save()

    $scope.save = (argToNav = null) ->

        #display the loading modal
        modalService.show(modalService.LOADING)

        #build the list to save
        listAnswerToSave = []
        for answer in $scope.answerList

            # test if the question was edited
            if answer.wasEdited != undefined && answer.wasEdited == true

                #test if the condition is not valid...
                if answer.hasValidCondition != undefined && answer.hasValidCondition == false
                    # clean the value
                    answer.value = null

                # add the answer of the listAnswerToSave
                listAnswerToSave[listAnswerToSave.length] = answer

        #console.log "listAnswerToSave"
        #console.log listAnswerToSave

        if listAnswerToSave.length == 0
            messageFlash.displayInfo translationService.get('ALL_ANSWERS_ALREADY_SAVED')

            modalService.close(modalService.LOADING)
        else
            #and replace the list
            $scope.o.answersSave.listAnswers = listAnswerToSave


            promise = $http
                method: "POST"
                url: '/awac/answer/save'
                headers:
                    "Content-Type": "application/json"
                data: $scope.o.answersSave

            promise.success (data, status, headers, config) ->
                messageFlash.displaySuccess translationService.get('ANSWERS_SAVED')
                modalService.close(modalService.LOADING)

                #refresh the progress bar

                listToRemove = []

                for key in Object.keys($scope.mapRepetition)
                    if key != '$$hashKey'
                        i=0
                        while i < $scope.mapRepetition[key].length
                            repetition =  $scope.mapRepetition[key][i]

                            founded=false
                            for answer in $scope.answerList
                                if answer.value? &&  (!answer.isAggregation? || answer.isAggregation != true) && $scope.compareRepetitionMap(answer.mapRepetition,repetition)
                                    founded=true
                                    break
                            if founded == false
                                it = listToRemove.length
                                listToRemove[it] = {}
                                listToRemove[it].code = key
                                listToRemove[it].iteration = {}
                                listToRemove[it].iteration[key] = $scope.mapRepetition[key][i][key]
                                j = angular.copy($scope.mapRepetition[key][i])

                                delete j[key]
                                listToRemove[it].map = j
                            i++

                for repetitionToRemove in listToRemove
                    $scope.removeIteration(repetitionToRemove.code,repetitionToRemove.iteration,repetitionToRemove.map)


                i=$scope.answerList.length-1
                while i>=0
                    answer = $scope.answerList[i]

                    # test if the question was edited
                    if answer.wasEdited == true
                        answer.wasEdited = false

                    if answer.toRemove == true
                        $scope.answerList.splice(i, 1)
                    i--

                $scope.saveFormProgress()

                #nav
                if argToNav != null
                    $scope.$root.$broadcast('NAV', argToNav)
                return

            promise.error (data, status, headers, config) ->
                messageFlash.displayError translationService.get('ERROR_THROWN_ON_SAVE') + data.message
                modalService.close(modalService.LOADING)
                return


    #
    # get list choice by question code
    #
    $scope.getUnitCategories = (code) ->
        if $scope.getQuestion(code) == null
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

    #
    # get the answer to compare by code and mapIteration
    # return null if this answer doesn't exist
    #
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
    $scope.getAnswerOrCreate = (code, mapIteration = null) ->
        if code == null || code == undefined
            console.log "ERROR !! getAnswerOrCreate : code is null or undefined"
            return null


        result = $scope.getAnswer(code, mapIteration)
        if result
            if result.toRemove?
                result.toRemove = false
            return result
        else
            #compute default value
            value = null
            defaultUnitId = null
            wasEdited = false
            if $scope.getQuestion(code) != null
                question = $scope.getQuestion(code)

                if question.defaultValue != null
                    value = question.defaultValue
                    wasEdited = true

                #compute defaultUnitId
                if question.unitCategoryId?
                    if question.defaultUnit?
                        defaultUnitId = question.defaultUnit.id
                    else
                        defaultUnitId = $scope.getUnitCategories(code).mainUnitId

            #if the answer was not founded, create it
            answerLine = {
                'questionKey': code
                'value': value
                'unitId': defaultUnitId
                'mapRepetition': mapIteration
                'lastUpdateUser': $scope.$root.currentPerson.identifier
                'wasEdited': wasEdited
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
        if mapRepetition?
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
            if question.mapRepetition? && $scope.compareRepetitionMap(question.mapRepetition, mapRepetition)
                if question.mapRepetition[questionSetCode] && question.mapRepetition[questionSetCode] == iterationToDelete[questionSetCode]
                    $scope.answerList[len].toRemove = true
                    if $scope.answerList[len].value != null
                        $scope.answerList[len].value = null
                        $scope.answerList[len].wasEdited = true

        # delete iteration
        # check all iteration because it must remove the iteration linked to the iteration to delete
        for key in Object.keys($scope.mapRepetition)
            if key != '$$hashKey'
                #value = mapContained[key]
                len = $scope.mapRepetition[key].length
                while (len--)
                    iteration = $scope.mapRepetition[key][len]
                    if $scope.compareRepetitionMap(iteration, mapRepetition) && iteration[questionSetCode] && iteration[questionSetCode] == iterationToDelete[questionSetCode]
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

    #
    # this function return an object with the modal service wanted (CONFIRMATION_EXIT_FORM)
    # and a valid parameter that is true is none answer was gave by the user.
    # Structure of the object :
    #   modalForConfirm => the constant of the modal expected
    #   valid => false if the confirmation modal is needed
    #
    # This function is used to display a warning modal if some data was not saved
    #
    $scope.validNavigation = ->
        result = {}
        result.modalForConfirm = modalService.CONFIRMATION_EXIT_FORM

        for answer in $scope.answerList
            if answer.wasEdited != undefined && answer.wasEdited == true
                result.valid = false
                break

        return result


    #
    # watch 'periodToCompare' variable and load the data to compare when the value is different than 'default'
    # the result is savec to $scope.dataToCompare
    #
    $scope.$parent.$watch 'periodToCompare', () ->
        if $scope.$parent != null && $scope.$parent.periodToCompare != 'default'
            promise = $http
                method: "GET"
                url: '/awac/answer/getByForm/' + $scope.formIdentifier + "/" + $scope.$parent.periodToCompare + "/" + $scope.$parent.scopeId
                headers:
                    "Content-Type": "application/json"
            promise.success (data, status, headers, config) ->
                $scope.dataToCompare = data
                return

            promise.error (data, status, headers, config) ->
                return
        else
            $scope.dataToCompare = null

    #
    # save the progresses of this form
    # compute a percentage of progression, refresh the progress bar displayed and
    # create a formProgressDTO and send it to the server
    #
    $scope.saveFormProgress = ->

        #compute percentage
        percentage = 0
        total = 0
        answered = 0

        listTotal = []

        for answer in $scope.answerList

            # document questions are optional : do not count them into total
            if $scope.getQuestion(answer.questionKey).answerType != 'DOCUMENT'

                if answer.hasValidCondition == undefined || answer.hasValidCondition == null || answer.hasValidCondition == true

                    if answer.isAggregation != true

                        # clean the value
                        total++
                        listTotal[listTotal.length] = answer

                        #test if the data is valid
                        if answer.value != null
                            answered++


        percentage = answered / total * 100

        percentage = Math.floor(percentage)


        #console.log "PROGRESS : " + answered + "/" + total + "=" + percentage
        #console.log listTotal

        #build formProgressDTO
        formProgressDTO = {}
        formProgressDTO.form = $scope.formIdentifier
        formProgressDTO.period = $scope.$parent.periodKey
        formProgressDTO.scope = $scope.$parent.scopeId
        formProgressDTO.percentage = percentage

        # refresh progress bar
        founded = false
        for formProgress in $scope.$parent.formProgress
            #console.log formProgress.form + "-" + $scope.formIdentifier
            if formProgress.form == $scope.formIdentifier
                founded = true
                formProgress.percentage = percentage
        if founded == false
            $scope.$parent.formProgress[$scope.$parent.formProgress.length] = formProgressDTO

        # send computed progress bar to the server
        promise = $http
            method: "POST"
            url: '/awac/answer/formProgress'
            headers:
                "Content-Type": "application/json"
            data: formProgressDTO
        promise.success (data, status, headers, config) ->
            return

    #
    # used at the last of the loading. Create the tabSet variable and add watcher for each questions concerned
    # by tabSet
    #
    $scope.createTabSet = ->
        for answer in $scope.answerList
            if answer.tabSet?

                tabSet = answer.tabSet
                tab = answer.tab

                if !$scope.tabSet[tabSet]?
                    $scope.tabSet[tabSet] = {}
                if !$scope.tabSet[tabSet][tab]?
                    $scope.tabSet[tabSet][tab] = {}

                if !$scope.tabSet[tabSet][tab].listToCompute?
                    $scope.tabSet[tabSet][tab].listToCompute = []
                i = $scope.tabSet[tabSet][tab].listToCompute.length

                $scope.tabSet[tabSet][tab].listToCompute[i] = answer

                $scope.createTabWatcher(tabSet, tab, i)

    #
    # create a watcher for a tab
    #
    $scope.createTabWatcher = (tabSet, tab, i)->
        $scope.$watch 'tabSet[' + tabSet + '][' + tab + '].listToCompute[' + i + '].value', ->
            $scope.computeTab(tabSet, tab)

    #
    # compute the progression of a tab
    #
    $scope.computeTab = (tabSet, tab)->
        isFinish = true

        for answer in $scope.tabSet[tabSet][tab].listToCompute
            if answer.value == null
                isFinish = false

        $scope.tabSet[tabSet][tab].isFinish=isFinish

        if isFinish == true
            if !$scope.tabSet[tabSet].master? || $scope.tabSet[tabSet].master > tab
                $scope.tabSet[tabSet].master = tab

        else if $scope.tabSet[tabSet].master == tab

            delete $scope.tabSet[tabSet].master

            for key in Object.keys($scope.tabSet[tabSet])
                if key != '$$hashKey' && key != 'master'
                    value = $scope.tabSet[tabSet][key]
                    if value.isFinish == true
                        if !$scope.tabSet[tabSet].master? || parseFloat($scope.tabSet[tabSet].master) > parseFloat key
                            $scope.tabSet[tabSet].master = parseFloat key

        if $scope.loading == true && $scope.tabSet[tabSet].master?
            for key in Object.keys($scope.tabSet[tabSet])
                if key != '$$hashKey' && key != 'master' && key != 'active'
                    if parseFloat(key) == parseFloat($scope.tabSet[tabSet].master)
                        $scope.tabSet[tabSet][key].active=true
                    else
                        $scope.tabSet[tabSet][key].active=false


