angular
.module('app.controllers')
.controller "FormCtrl", ($scope, downloadService, messageFlash, translationService, modalService, $route, $timeout, $filter) ->
    $scope.forms = {
        enterprise: ['TAB2', 'TAB3', 'TAB4', 'TAB5', 'TAB6', 'TAB7'],
        household: ['TAB_M1', 'TAB_M2', 'TAB_M3', 'TAB_M4', 'TAB_M5'],
        littleemitter: ['TAB_P1', 'TAB_P2', 'TAB_P3', 'TAB_P4', 'TAB_P5', 'TAB_P6'],
        municipality: ['TAB_C1', 'TAB_C2', 'TAB_C3', 'TAB_C4', 'TAB_C5'],
        event: ['TAB_EV1', 'TAB_EV2', 'TAB_EV3', 'TAB_EV4', 'TAB_EV5', 'TAB_EV6']
    }

    $scope.displayFormMenu = true

    $scope.init = (route) ->
        $scope.formIdentifier = route

        array = $scope.forms[$scope.$root.instanceName]
        idx = _.indexOf(array, route)
        $scope.previousFormIdentifier = null
        if idx > 0
            $scope.previousFormIdentifier = array[idx - 1]
        $scope.nextFormIdentifier = null
        if idx < array.length - 1
            $scope.nextFormIdentifier = array[idx + 1]

        # store the structure and the status of tabSet and tab
        # structure :
        # tabSet[number of the tabSet][].master
        # tabSet[number of the tabSet][].mapRepetition
        # tabSet[number of the tabSet][][number of the set].master
        #
        $scope.tabSet = {}

        # declare the dataToCompare variable. This variable is used to display data to compare
        # the content is a FormDTO object (after loading by the $scope.$parent.$watch function
        $scope.dataToCompare = null

        #this variable contains all answer, new and old
        $scope.answerList = []
        $scope.mapRepetition = {}
        $scope.mapQuestionSet = {}
        ###
        illustration of the structures
        $scope.mapRepetition['A15'] = [{'A15':1},
                                        {'A15':2}]

        $scope.mapRepetition['A16'] = [{'A16':1,'A15':1},
                                       {'A16':2,'A15':1}]
        ###

        ###
        list of the questionSet key locked with locker like personDTO
        ###
        $scope.datalocker = {}
        $scope.dataValidator = {}
        $scope.dataVerification = {}

        $scope.$on 'CLEAN_VERIFICATION', ->
            $scope.dataVerification = []

        $scope.loading = true
        $scope.errorMessage = null

        #display the loading modal
        modalService.show(modalService.LOADING)

        #
        # load the form and treat structure and data
        #
        downloadService.getJson "/awac/answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$root.periodSelectedKey + "/" + $scope.$root.scopeSelectedId, (result) ->
            if not result.success
                if !!result.data.messageToTranslate
                    $scope.errorMessage = $filter('translateText')(result.data.messageToTranslate)
                else
                    $scope.errorMessage = result.data.message
                modalService.close(modalService.LOADING)
            else

                #display
                console.log "LOADED DATA : "
                console.log result.data

                # save last form loaded
                $scope.$root.lastPeriodSelectedKey = $scope.$root.periodSelectedKey
                $scope.$root.lastScopeSelectedId = $scope.$root.scopeSelectedId
                $scope.$root.lastFormIdentifier = $scope.formIdentifier

                $scope.o = angular.copy(result.data)

                #build the list of answers
                #recove answerSave
                if $scope.o.answersSave != null && $scope.o.answersSave != undefined
                    answerSave = $scope.o.answersSave
                    #save answer        i
                    $scope.answerList = answerSave.listAnswers

                #
                # compute mapRepetition
                #
                $scope.loopRepetition = (questionSetDTO, listQuestionSetRepetition = [], parent) ->
                    if questionSetDTO.datalocker?
                        $scope.datalocker[questionSetDTO.code] = questionSetDTO.datalocker
                    if questionSetDTO.dataValidator?
                        $scope.dataValidator[questionSetDTO.code] = questionSetDTO.dataValidator
                    if questionSetDTO.verification?
                        $scope.dataVerification[questionSetDTO.code] = questionSetDTO.verification


                    questionSetElement =
                        questionSetDTO: questionSetDTO
                        parent: parent

                    $scope.mapQuestionSet[questionSetDTO.code] = questionSetElement

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
                            $scope.loopRepetition(child, angular.copy(listQuestionSetRepetition), questionSetElement)

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

                if $scope.$root?
                    $scope.$root.$broadcast("REFRESH_LAST_SAVE_TIME", args)


                #build list of repetition for the mmAwacRepetition
                for qSet in $scope.o.questionSets
                    $scope.loopRepetition(qSet)

                #use the defaultValues to completed null value
                for questionSetDTO in $scope.o.questionSets
                    $scope.addDefaultValue(questionSetDTO)

                $timeout(->
                    for answer in $scope.answerList
                        $scope.createTabWatcher answer
                    $timeout(->
                        modalService.close(modalService.LOADING)
                        $scope.loading = false

                        # remove value from answer with a false condition
                        for answer in $scope.answerList
                            if answer.hasValidCondition == false && answer.value != null
                                answer.value = null

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

            #eject locked or validate question
            finalList = []

            for answer in listAnswerToSave
                if not $scope.isQuestionLocked(answer.questionKey) && not $scope.isQuestionValidate(answer.questionKey)
                    finalList.push answer

            if finalList.length == 0
                messageFlash.displayInfo translationService.get('ALL_ANSWERS_ALREADY_SAVED')

                modalService.close(modalService.LOADING)
            else
                #and replace the list
                $scope.o.answersSave.listAnswers = finalList


                console.log "SAVE"
                console.log $scope.o.answersSave

                downloadService.postJson '/awac/answer/save', $scope.o.answersSave, (result) ->
                    if result.success

                        #refresh the progress bar

                        listToRemove = []

                        for key in Object.keys($scope.mapRepetition)
                            if key != '$$hashKey'
                                i = 0
                                while i < $scope.mapRepetition[key].length
                                    repetition = $scope.mapRepetition[key][i]

                                    founded = false
                                    for answer in $scope.answerList
                                        if answer.value? && (!answer.isAggregation? || answer.isAggregation != true) && $scope.compareRepetitionMap(answer.mapRepetition, repetition)
                                            founded = true
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
                            $scope.removeIteration(repetitionToRemove.code, repetitionToRemove.iteration, repetitionToRemove.map)

                        #
                        # normalize answer after save
                        #
                        i = $scope.answerList.length - 1
                        while i >= 0
                            answer = $scope.answerList[i]

                            # test if the question was edited
                            if answer.wasEdited == true
                                answer.wasEdited = false

                            if answer.toRemove == true
                                $scope.answerList.splice(i, 1)
                            i--

                        #
                        # refresh the progressBar
                        #
                        $scope.saveFormProgress()

                        #
                        # display success message and hide the loading modal
                        #
                        messageFlash.displaySuccess translationService.get('ANSWERS_SAVED')
                        modalService.close(modalService.LOADING)

                        #nav
                        if argToNav != null
                            $scope.$root.nav(argToNav.loc, argToNav.confirmed)
                        return

                    else
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


        $scope.getAnswerToCompare = (code, useless, mapIndex) ->
            if $scope.dataToCompare != null
                # convert index to id
                if mapIndex?
                    mapIteration = {}
                    for indexKey in Object.keys(mapIndex)
                        if $scope.mapRepetitionEquivalenceForComparison[indexKey]?
                            for key in Object.keys($scope.mapRepetitionEquivalenceForComparison[indexKey])
                                if $scope.mapRepetitionEquivalenceForComparison[indexKey][key] == mapIndex[indexKey]
                                    repetitionKey = key

                                    # build the iteration map
                                    mapIteration[indexKey] = parseFloat(repetitionKey)

                    if Object.keys(mapIteration).length == 0
                        return null

                for answer in $scope.dataToCompare.answersSave.listAnswers
                    #control the code
                    if answer.questionKey == code

                        #control the repetition map
                        if $scope.compareRepetitionMap(answer.mapRepetition, mapIteration)
                            return answer

            return null

        #
        # get the answer to compare by code and mapIteration
        # return null if this answer doesn't exist
        #
        ###
        $scope.getAnswerToCompare = (code, mapIteration,index) ->

            if $scope.dataToCompare != null
                i=0
                for answer in $scope.dataToCompare.answersSave.listAnswers
                    if answer.questionKey == code
                        if i == index
                            return answer
                        i++
            return null


        #
        # get the first repetition
        #
        $scope.getFirstRepeteableElement = (questionSetCode) ->
            if $scope.mapQuestionSet[questionSetCode].repetitionAllowed == true
                return questionSetCode
            else
                return getFirstRepeteableElement($scope.mapQuestionSet[questionSetCode].parent.code)

        ###


        #
        # get the answer by code and mapIteration
        # if there is not answer for this case, create it
        #
        $scope.getAnswerOrCreate = (code, mapIteration, tabSet = null, tab = null, optional = false) ->
            if code == null || code == undefined
                return null


            result = $scope.getAnswer(code, mapIteration)
            if result
                if result.toRemove?
                    result.toRemove = false
                if tabSet? && !result.tabSet?
                    result.tabSet = tabSet
                    result.tab = tab

                result.optional = optional
                return result
            else
                #compute default value
                value = null
                defaultUnitCode = null
                wasEdited = false
                if $scope.getQuestion(code) != null
                    question = $scope.getQuestion(code)

                    if question.defaultValue != null
                        value = question.defaultValue
                        wasEdited = true

                    #compute defaultUnitCode
                    if question.unitCategoryId?
                        if question.defaultUnit?
                            defaultUnitCode = question.defaultUnit.code
                        else
                            defaultUnitCode = $scope.getUnitCategories(code).mainUnitCode

                #if the answer was not founded, create it
                answerLine = {
                    'questionKey': code
                    'value': value
                    'unitCode': defaultUnitCode
                    'mapRepetition': mapIteration
                    'lastUpdateUser': $scope.$root.currentPerson
                    'wasEdited': wasEdited
                }

                if tabSet?
                    answerLine.tabSet = tabSet
                    answerLine.tab = tab

                # add optional for all
                answerLine.optional = optional

                $scope.createTabWatcher answerLine

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


        $scope.mapRepetitionEquivalenceForComparison = {}

        #
        # watch 'periodToCompare' variable and load the data to compare when the value is different than 'default'
        # the result is savec to $scope.dataToCompare
        #
        $scope.$watch '$root.periodToCompare', () ->
            if $scope.$parent != null && $scope.$root.periodToCompare? && $scope.$root.periodToCompare != 'default'
                downloadService.getJson '/awac/answer/getByForm/' + $scope.formIdentifier + "/" + $scope.$root.periodToCompare + "/" + $scope.$root.scopeSelectedId, (result)->
                    if result.success
                        $scope.dataToCompare = result.data

                        # build the repetition equivalence table
                        for answer in result.data.answersSave.listAnswers
                            if answer.mapRepetition? && Object.keys(answer.mapRepetition).length > 0
                                for repetitionKey in Object.keys(answer.mapRepetition)
                                    index = 0
                                    if not $scope.mapRepetitionEquivalenceForComparison[repetitionKey]
                                        $scope.mapRepetitionEquivalenceForComparison[repetitionKey] = {}
                                    else
                                        index = Object.keys($scope.mapRepetitionEquivalenceForComparison[repetitionKey]).length
                                    if !$scope.mapRepetitionEquivalenceForComparison[repetitionKey][answer.mapRepetition[repetitionKey]]?
                                        $scope.mapRepetitionEquivalenceForComparison[repetitionKey][answer.mapRepetition[repetitionKey]] = index
                    else
                        $scope.dataToCompare = null
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


            # compute value for non tab-set answer
            for answer in $scope.answerList

                if !answer.tabSet?

                    # remove optional or aggregation
                    if answer.optional != true && answer.isAggregation != true

                        if answer.hasValidCondition == undefined || answer.hasValidCondition == null || answer.hasValidCondition == true

                            # clean the value
                            total++
                            listTotal.push answer

                            #test if the data is valid
                            if answer.value != null
                                answered++

            # compute value for non tab-set answer
            for key in Object.keys($scope.tabSet)
                if key != '$$hashKey'
                    for tabSet in $scope.tabSet[key]
                        if tabSet.master?
                            for answer in $scope.answerList
                                if answer.tabSet? && parseFloat(answer.tabSet) == parseFloat(key) && parseFloat(answer.tab) == parseFloat(tabSet.master)

                                    if answer.optional != true && answer.isAggregation != true
                                        if answer.hasValidCondition == undefined || answer.hasValidCondition == null || answer.hasValidCondition == true
                                            total++
                                            listTotal.push answer

                                            #test if the data is valid
                                            if answer.value != null
                                                answered++
                        else
                            for answer in $scope.answerList
                                if answer.tabSet? && parseFloat(answer.tabSet) == parseFloat(key) && parseFloat(answer.tab) == 1

                                    if answer.optional != true && answer.isAggregation != true
                                        if answer.hasValidCondition == undefined || answer.hasValidCondition == null || answer.hasValidCondition == true
                                            total++
                                            listTotal.push answer

                                            #test if the data is valid
                                            if answer.value != null
                                                answered++
            if answered == 0
                percentage = 0
            else
                percentage = answered / total * 100
                percentage = Math.floor(percentage)

            #build formProgressDTO
            formProgressDTO = {}
            formProgressDTO.form = $scope.formIdentifier
            formProgressDTO.period = $scope.$root.periodSelectedKey
            formProgressDTO.scope = $scope.$root.scopeSelectedId
            formProgressDTO.percentage = percentage

            # refresh progress bar
            founded = false
            if $scope.$parent.formProgress?
                for formProgress in $scope.$parent.formProgress
                    #console.log formProgress.form + "-" + $scope.formIdentifier
                    if formProgress.form == $scope.formIdentifier
                        founded = true
                        formProgress.percentage = percentage
            else
                $scope.$parent.formProgress = []

            if founded == false
                $scope.$parent.formProgress[$scope.$parent.formProgress.length] = formProgressDTO

            # send computed progress bar to the server
            downloadService.postJson '/awac/answer/formProgress', formProgressDTO, (result) ->
                if result.success
                    return

        $scope.addTabSet = (tabSet, tab, mapRepetition) ->
            ite = null
            wasCreated = false
            if !$scope.tabSet[tabSet]?
                $scope.tabSet[tabSet] = []
                $scope.tabSet[tabSet][0] = {}
                $scope.tabSet[tabSet][0].mapRepetition = mapRepetition
                ite = 0
                wasCreated = true
            else
                i = 0
                while i < $scope.tabSet[tabSet].length
                    if $scope.compareRepetitionMap(mapRepetition, $scope.tabSet[tabSet][i].mapRepetition)
                        ite = i
                        break
                    i++
                if ite == null
                    ite = $scope.tabSet[tabSet].length
                    $scope.tabSet[tabSet][ite] = {}
                    $scope.tabSet[tabSet][ite].mapRepetition = mapRepetition

            if !$scope.tabSet[tabSet][ite][tab]?
                $scope.tabSet[tabSet][ite][tab] = {}
                $scope.tabSet[tabSet][ite][tab].active = (tab == 1 ? true: false)
                wasCreated = true

            if !$scope.tabSet[tabSet][ite][tab].listToCompute?
                $scope.tabSet[tabSet][ite][tab].listToCompute = []

            return ite


        $scope.getStatusClassForTab = (tabSet, tab, mapRepetition) ->

            # by default, the tab is not finish
            isFinish = true

            #find the good iteration
            i = 0
            while i < $scope.tabSet[tabSet].length
                if $scope.compareRepetitionMap(mapRepetition, $scope.tabSet[tabSet][i].mapRepetition)
                    ite = i
                    break
                i++

            allWasSave = true
            atLeastOneQuestion = false

            # browse the list of answer include into this tab
            for answer in $scope.tabSet[tabSet][ite][tab].listToCompute
                #
                # optional, aggregation or with false condition are not take in this case
                #
                if !answer.hasValidCondition? || answer.hasValidCondition == true && answer.optional != true && answer.isAggregation != true
                    atLeastOneQuestion = true
                    if answer.wasEdited != undefined && answer.wasEdited == true
                        allWasSave = false
                    # if one of this answer are a value == null, the tab is not finish => break
                    if answer.value == null
                        isFinish = false

            if isFinish == true && atLeastOneQuestion == false
                isFinish = false

            #return css
            if isFinish == true
                if allWasSave == false
                    return 'answer_temp'
                return 'answer'
            else
                if allWasSave == false
                    return 'pending_temp'
                return 'pending'

        #
        # create a watcher for an answer : all new answer or loaded answer use this function
        #
        $scope.createTabWatcher = (answer)->

            # test if the answer have a tabSet
            if answer.tabSet?

                tabSet = answer.tabSet
                tab = answer.tab

                ite = $scope.addTabSet(tabSet, tab, answer.mapRepetition)

                # test if an answer with the same questionKey / mapRepetition is already contains into the list of answer
                j = $scope.tabSet[tabSet][ite][tab].listToCompute.length
                j--
                while j >= 0
                    answerToTest = $scope.tabSet[tabSet][ite][tab].listToCompute[j]
                    if answerToTest.questionKey == answer.questionKey && $scope.compareRepetitionMap(answer.mapRepetition, answerToTest.mapRepetition)
                        #... and remove if it's needed
                        $scope.tabSet[tabSet][ite][tab].listToCompute.splice(j, 1)
                    j--

                i = $scope.tabSet[tabSet][ite][tab].listToCompute.length

                # add to the list
                $scope.tabSet[tabSet][ite][tab].listToCompute[i] = answer

                # add a watcher into the answer
                $scope.$watch 'tabSet[' + tabSet + '][' + ite + '][' + tab + '].listToCompute[' + i + '].value', ->
                    $scope.computeTab(tabSet, tab, answer.mapRepetition)

                if $scope.loading == false
                    $scope.computeTab(tabSet, tab, answer.mapRepetition)

                # refresh isFinish if it's needed
                if answer.value == null
                    $scope.tabSet[tabSet][ite][tab].isFinish = false

        #
        # compute the progression of a tab
        #
        $scope.computeTab = (tabSet, tab, mapRepetition)->

            # by default, the tab is not finish
            isFinish = false

            #find the good iteration
            i = 0
            while i < $scope.tabSet[tabSet].length
                if $scope.compareRepetitionMap(mapRepetition, $scope.tabSet[tabSet][i].mapRepetition)
                    ite = i
                    break
                i++

            # browse the list of answer include into this tab
            for answer in $scope.tabSet[tabSet][ite][tab].listToCompute
                #
                # optional, aggregation or with false condition are not take in this case
                #
                if !answer.hasValidCondition? || answer.hasValidCondition == true && answer.optional != true && answer.isAggregation != true
                    # if one of this answer are a value == null, the tab is not finish => break
                    if answer.value == null
                        isFinish = false
                        break
                    else
                        # if the value is not null, isFinish = true => broke is an other answer have value == null
                        isFinish = true

            if isFinish != $scope.tabSet[tabSet][ite][tab].isFinish

                # compute the new master
                $scope.tabSet[tabSet][ite][tab].isFinish = isFinish

                if isFinish == true
                    if !$scope.tabSet[tabSet][ite].master? || $scope.tabSet[tabSet][ite].master > tab
                        $scope.tabSet[tabSet][ite].master = tab

                else if $scope.tabSet[tabSet][ite].master == tab

                    delete $scope.tabSet[tabSet][ite].master

                    for key in Object.keys($scope.tabSet[tabSet][ite])
                        if key != '$$hashKey' && key != 'master' && key != 'active' && key != 'mapRepetition'
                            value = $scope.tabSet[tabSet][ite][key]
                            if value.isFinish == true
                                if !$scope.tabSet[tabSet][ite].master? || parseFloat($scope.tabSet[tabSet][ite].master) > parseFloat key
                                    $scope.tabSet[tabSet][ite].master = parseFloat key

                if $scope.loading == true && $scope.tabSet[tabSet][ite].master?
                    for key in Object.keys($scope.tabSet[tabSet][ite])
                        if key != '$$hashKey' && key != 'master' && key != 'active' && key != 'mapRepetition'
                            if parseFloat(key) == parseFloat($scope.tabSet[tabSet][ite].master)
                                $scope.tabSet[tabSet][ite][key].active = true
                            else
                                $scope.tabSet[tabSet][ite][key].active = false

        #
        # return a tab object. This function is used by tab.active.
        # if the loading is not finish yet, return a fake object with active = undefined
        #
        $scope.getTab = (tabSet, tab, mapRepetition = null) ->
            if $scope.loading == true || !$scope.tabSet[tabSet]?
                $scope.addTabSet(tabSet, tab, mapRepetition)
            for tabSetToTest in $scope.tabSet[tabSet]
                if $scope.compareRepetitionMap(mapRepetition, tabSetToTest.mapRepetition)
                    if !tabSetToTest[tab]?
                        tabSetToTest[tab] = {}
                        tabSetToTest[tab].active = (parseFloat(tab) == 1 ? true: false)
                        return tabSetToTest[tab]
                    return tabSetToTest[tab]
            return null

        #
        # return true is the asked tab is the master of the tabSet
        # return false if the loading is not finish
        #
        $scope.tabIsMaster = (tabSet, tab, mapRepetition = null) ->
            if $scope.loading == true || !$scope.tabSet[tabSet]?
                return false
            for tabSetToTest in $scope.tabSet[tabSet]
                if $scope.compareRepetitionMap(mapRepetition, tabSetToTest.mapRepetition)
                    if tabSetToTest.master == parseFloat(tab)
                        return true
                    return false
            return false


        #
        #
        #
        $scope.getQuestionSetLocker = (code) ->
            if $scope.datalocker[code]?
                return $scope.datalocker[code]
            return null

        #
        #
        #
        $scope.getQuestionSetValidator = (code) ->
            if $scope.dataValidator[code]?
                return $scope.dataValidator[code]
            return null

        #
        #
        #
        $scope.isQuestionLocked = (code) ->
            for key in Object.keys($scope.mapQuestionSet)
                if key != '$$hashKey'
                    questionSet = $scope.mapQuestionSet[key]
                    if questionSet.questionSetDTO.code == code
                        result = $scope.foundBasicParent(questionSet)
                        return result.datalocker? && result.datalocker.identifier != $scope.$root.currentPerson.identifier
                    if questionSet.questionSetDTO.questions?
                        for question in questionSet.questionSetDTO.questions
                            if question.code == code
                                result = $scope.foundBasicParent(questionSet)
                                return result.datalocker? && result.datalocker.identifier != $scope.$root.currentPerson.identifier

        $scope.isQuestionValidate = (code) ->
            if $scope.getValidatorByQuestionCode(code)?
                return true
            return false

        $scope.getValidatorByQuestionCode = (code) ->
            for key in Object.keys($scope.mapQuestionSet)
                if key != '$$hashKey'
                    questionSet = $scope.mapQuestionSet[key]
                    if questionSet.questionSetDTO.code == code
                        return $scope.foundBasicParent(questionSet).dataValidator
                    if questionSet.questionSetDTO.questions?
                        for question in questionSet.questionSetDTO.questions
                            if question.code == code
                                return  $scope.foundBasicParent(questionSet).dataValidator
            return null


        #
        #
        #
        $scope.lockQuestionSet = (code, lock) ->
            if lock == true
                if not $scope.datalocker[code]?
                    $scope.datalocker[code] = $scope.$root.currentPerson
                $scope.mapQuestionSet[code].questionSetDTO.datalocker = $scope.$root.currentPerson
            else
                if $scope.datalocker[code]?
                    delete $scope.datalocker[code]
                $scope.mapQuestionSet[code].questionSetDTO.datalocker = null

        #
        #
        #
        $scope.validateQuestionSet = (code, lock) ->
            if lock == true
                if not $scope.dataValidator[code]?
                    $scope.dataValidator[code] = $scope.$root.currentPerson
                if $scope.mapQuestionSet[code]?
                    $scope.mapQuestionSet[code].questionSetDTO.dataValidator = $scope.$root.currentPerson
            else
                if $scope.dataValidator[code]?
                    delete $scope.dataValidator[code]
                $scope.mapQuestionSet[code].questionSetDTO.dataValidator = null

        $scope.foundBasicParent = (questionSet)->
            if !questionSet.parent?
                return questionSet.questionSetDTO
            return $scope.foundBasicParent(questionSet.parent)

        $scope.verifyQuestionSet = (code, verification) ->
            $scope.dataVerification[code] = verification


        $scope.getQuestionSetVerification = (code) ->
            if $scope.dataVerification[code]?
                return $scope.dataVerification[code]
            return null


    # test key and scope
    if($route.current.params.form?)
        $scope.init($route.current.params.form)
