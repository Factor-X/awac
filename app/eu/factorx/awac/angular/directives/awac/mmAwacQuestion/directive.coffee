angular
.module('app.directives')
.directive "mmAwacQuestion", (directiveService, translationService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
        ngCondition: '='
        ngRepetitionMap: '='
    templateUrl: "$/angular/templates/mm-awac-question.html"
    replace: true
    link: (scope,element) ->
        directiveService.autoScopeImpl scope

        #
        # this function return the specific template for the
        # question in function of the question type, get by the model
        # dataToCompare : if true, the data is only for comparison
        #   => load the template with true for the dataToCompare parameter
        #
        scope.getTemplate = (dataToCompare)->

            toCompare = ""
            if  dataToCompare == true
                toCompare = "ToCompare"

            if scope.getQuestion() != null
                answerType = scope.getQuestion().answerType

                #call the directive by the type of the question
                if answerType == 'BOOLEAN'
                    return "mmAwacBooleanQuestion"+toCompare
                else if answerType == 'INTEGER'
                    return "mmAwacIntegerQuestion"+toCompare
                else if answerType == 'DOUBLE'
                    if scope.getQuestion().unitCategoryId != null || scope.getQuestion().unitCategoryId != undefined
                        return "mmAwacRealWithUnitQuestion"+toCompare
                    else
                        return "mmAwacRealQuestion"+toCompare
                else if answerType == 'PERCENTAGE'
                    return "mmAwacPercentageQuestion"+toCompare
                else if answerType == 'STRING'
                    return "mmAwacStringQuestion"+toCompare
                else if answerType == 'VALUE_SELECTION'
                    return "mmAwacSelectQuestion"+toCompare
                else if answerType == 'DOCUMENT'
                    return "mmAwacDocumentQuestion"+toCompare


        scope.getQuestion = ->
            return scope.$parent.getQuestion(scope.getQuestionCode())


        #
        # get the answer for comparison.
        # call the getAnswerToCompare from $parent
        #
        scope.getAnswerToCompare = ->
            return scope.$parent.getAnswerToCompare(scope.getQuestionCode(),scope.getRepetitionMap())

        #
        # get the answer
        # call the getAnswerOrCreate from $parent
        #
        scope.getAnswerValue = () ->
            return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap())

        #
        # true if there is a description for this question
        # get by the model
        #
        scope.hasDescription = () ->
            return translationService.get(scope.getQuestionCode() + '_DESC') != null

        #
        # return the answer
        # forDataToCompare => return the answer for dataToCompare or not
        # if not, call the answerOrCreate parent method => return always something
        # if true, return an answer only if this answer already exists
        #
        scope.getAnswer = (forDataToCompare=false)->
            if forDataToCompare
                return scope.$parent.getAnswerToCompare(scope.getQuestionCode(), scope.getRepetitionMap())
            else
                return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap())

        #
        # return the list of unit for the question
        # call the getUnitCategories parent method
        #
        scope.getUnitCategories = ->
            return scope.$parent.getUnitCategories(scope.getQuestionCode())

        #
        # return the list of options for the question
        # call the getCodeList parent method
        #
        scope.getCodeList = ->
            return scope.$parent.getCodeList(scope.getQuestionCode())


        #
        # true if there is data to compare to display
        #
        scope.displayOldDatas = ->
            if scope.$parent.dataToCompare == null
                return false
            return true

        #
        # called when the user change the value of the field
        #
        scope.edited = ->
            console.log "EDITED !!!"
            if scope.getAnswerValue().value !=null
                if scope.getAnswerValue().value.length == 0
                    scope.getAnswerValue().value = null
            scope.getAnswerValue().wasEdited = true
            scope.getAnswerValue().lastUpdateUser = scope.$root.currentPerson.identifier


        #
        # create a event CONDITION if the ngCondition was modified
        #
        scope.$watch 'ngCondition', () ->
            scope.$root.$broadcast('CONDITION')

        #
        # call the CONDITION event :
        # test the condition of this element
        #
        scope.$on 'CONDITION',(event,args) ->
            scope.testVisibility(element)

        #
        # test if the condition of this element
        # change the hasValidCondition in function and
        # change the value by default value if
        # the value is null and the condition is true
        #
        scope.firstComputecondition=true
        scope.testVisibility = (elementToTest)->

            #if scope.getCondition() != undefined && scope.$parent.loading != true

                # if the element contains the condition-false class,
                # the condition of this question is false
                if elementToTest.hasClass('condition-false') == true

                    # if there was modification the the validity of the condition ...
                    if scope.getAnswerValue().hasValidCondition != false
                        #...change the parameter into the answer
                        scope.getAnswerValue().hasValidCondition = false
                        if scope.getAnswerValue().value != null
                            scope.getAnswerValue().value = null
                            scope.$root.$broadcast('CONDITION')

                            scope.getAnswerValue().wasEdited = true
                            scope.getAnswerValue().lastUpdateUser = scope.$root.currentPerson.identifier

                    return false

                # the body if the limit for the test => continue if this element id not the body
                if elementToTest.parent()[0].tagName!='BODY'
                    return scope.testVisibility(elementToTest.parent())
                else
                    # if this element is the body, the condition is true
                    # if there was modification the the validity of the condition ...
                    if  scope.getAnswerValue().hasValidCondition != true
                        #...change the parameter into the answer
                        scope.getAnswerValue().hasValidCondition = true

                        # try to add a default value
                        if scope.getQuestion()!=null && scope.getQuestion().defaultValue !=null
                            scope.getAnswerValue().value = scope.getQuestion().defaultValue

                            # if this if the first compute, it was caused during the loading :
                            # the question is not edited
                            if scope.firstComputecondition == true
                                scope.firstComputecondition=false
                            else
                                scope.getAnswerValue().wasEdited = true
                                scope.getAnswerValue().lastUpdateUser = scope.$root.currentPerson.identifier

                    return true

        #
        # return the name of the user for this question
        # forDataToCompare => return the name of the user for the dataToCompare
        # initialOnly => return initials of the name, or complete name
        #
        scope.getUserName = (forDataToCompare,initialOnly)->
            user = null

            if forDataToCompare == true
                if scope.displayOldDatas() && scope.getAnswerToCompare() != null
                    user = scope.$root.getUserByIdentifier(scope.getAnswerToCompare().lastUpdateUser)
            else
                if scope.getAnswerValue() != null
                    user = scope.$root.getUserByIdentifier(scope.getAnswerValue().lastUpdateUser)

            if user == null
                return ""
            else if initialOnly
                initial = user.firstName.substring(0,1)+user.lastName.substring(0,1)
                return initial
            else
                name = user.firstName+" "+user.lastName
                return name

        #
        # get the status class
        #
        scope.getStatusClass =->

            #there is no status for document
            if scope.getQuestion()!=null && scope.getQuestion().answerType == 'DOCUMENT'
                return ""

            answer = scope.getAnswerValue()

            if answer.value!=null && (answer.$valid == null || answer.value.$valid == undefined || answer.value.$valid == true)
                if answer.wasEdited != undefined  && answer.wasEdited == true
                    return 'answer_temp'
                return 'answer'
            else
                if answer.wasEdited != undefined  && answer.wasEdited == true
                    return 'pending_temp'
                return 'pending'









