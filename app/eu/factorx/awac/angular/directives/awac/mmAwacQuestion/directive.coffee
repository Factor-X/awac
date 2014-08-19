angular
.module('app.directives')
.directive "mmAwacQuestion", (directiveService, translationService, $compile, $timeout, modalService) ->
    restrict: "E"
    scope: directiveService.autoScope
    # the code of the question
        ngQuestionCode: '='
    # a condition : optional
        ngCondition: '='
    # the repetition map of the question if the question is into a repetition
        ngRepetitionMap: '='
    # an aggregation : if the attribu is not empty, the question cannot be edited
        ngAggregation: '='
    #
        ngTabSet:'='
    #
        ngTab:'='
    templateUrl: "$/angular/templates/mm-awac-question.html"
    replace: true
    compile: () ->
        post: (scope, element) ->
            directiveService.autoScopeImpl scope

            scope.$watch 'ngAggregation', ->
                if scope.getAggregation()?
                    scope.getAnswer().value = scope.getAggregation()

            #
            # watch the loading of the data into the parent and
            # use the getTemplate to build the question body with the expected directive
            #
            scope.$watch '$parent.o', ->
                scope.getTemplate(true)
                scope.getTemplate(false)

            #
            # this function return the specific template for the
            # question in function of the question type, get by the model
            # dataToCompare : if true, the data is only for comparison
            #   => load the template with true for the dataToCompare parameter
            #
            scope.getTemplate = (dataToCompare)->
                if $('.inject-data:first', element).html() == ''


                    toCompare = ""
                    isAggregation = "false"
                    directiveName = ""


                    if  dataToCompare == true
                        toCompare = "true"
                    else
                        toCompare = "false"

                    if scope.getAggregation()?
                        isAggregation = "true"

                    if scope.getQuestion() != null
                        answerType = scope.getQuestion().answerType

                        #call the directive by the type of the question
                        if answerType == 'BOOLEAN'
                            directiveName = "boolean-question"
                        else if answerType == 'INTEGER'
                            directiveName = "integer-question"
                        else if answerType == 'DOUBLE'
                            if scope.getQuestion().unitCategoryId != null || scope.getQuestion().unitCategoryId != undefined
                                directiveName = "real-with-unit-question"
                            else
                                directiveName = "real-question"
                        else if answerType == 'PERCENTAGE'
                            directiveName = "percentage-question"
                        else if answerType == 'STRING'
                            directiveName = "string-question"
                        else if answerType == 'VALUE_SELECTION'
                            directiveName = "select-question"
                        else if answerType == 'DOCUMENT'
                            directiveName = "document-question"

                        directive = $compile("<mm-awac-" + directiveName + " ng-data-to-compare=\"" + toCompare + "\" ng-is-aggregation=\"" + isAggregation + "\"></mm-awac-" + directiveName + ">")(scope)

                        if dataToCompare == true
                            $('.inject-data-to-compare:first', element).append(directive)
                        else
                            $('.inject-data:first', element).append(directive)


            scope.getQuestion = ->
                return scope.$parent.getQuestion(scope.getQuestionCode())

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
            scope.getAnswer = (forDataToCompare = false)->
                if forDataToCompare
                    return scope.$parent.getAnswerToCompare(scope.getQuestionCode(), scope.getRepetitionMap())
                else
                    if scope.getTabSet()? && !scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap()).tabSet?
                        scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap()).tabSet =scope.getTabSet()
                        scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap()).tab =scope.getTab()
                    if scope.ngAggregation? && scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap()).value == null
                        scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap()).value = scope.getAggregation()
                        scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap()).isAggregation = true
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
                if scope.getAggregation()?
                    return
                else
                    if scope.getAnswer().value != null
                        if scope.getAnswer().value.length == 0
                            scope.getAnswer().value = null
                    scope.getAnswer().wasEdited = true
                    scope.getAnswer().lastUpdateUser = scope.$root.currentPerson.identifier


            #
            # create a event CONDITION if the ngCondition was modified
            #
            scope.$watch 'ngCondition', (n, o) ->
                if n != o
                    scope.$root.$broadcast('CONDITION')

            #
            # call the CONDITION event :
            # test the condition of this element
            #
            scope.$on 'CONDITION', (event, args) ->
                scope.testVisibility(element)

            #
            # test if the condition of this element
            # change the hasValidCondition in function and
            # change the value by default value if
            # the value is null and the condition is true
            #
            scope.firstComputecondition = true
            scope.testVisibility = (elementToTest)->

                # if the element contains the condition-false class,
                # the condition of this question is false
                if elementToTest.hasClass('condition-false') == true || (scope.getCondition()? && scope.getCondition() == false)

                    # print used for debug
                    # console.log "je suis "+scope.getQuestionCode()+" et me condition est fausse : loading : "+scope.$parent.loading+", value : "+scope.getAnswer().value

                    # if there was modification the the validity of the condition ...
                    if scope.getAnswer().hasValidCondition != false
                        #...change the parameter into the answer
                        scope.getAnswer().hasValidCondition = false
                        if scope.getAnswer().value != null

                            if scope.$parent.loading == false
                                scope.getAnswer().value = null
                                scope.edited()
                                scope.$root.$broadcast('CONDITION')
                            else
                                scope.getAnswer().wasEdited = false

                    return false

                # the body if the limit for the test => continue if this element id not the body
                if elementToTest.parent()[0].tagName != 'BODY'
                    return scope.testVisibility(elementToTest.parent())
                else
                    # print used for debug
                    # console.log "je suis "+scope.getQuestionCode()+" et me condition est vraie : loading : "+scope.$parent.loading+", value : "+scope.getAnswer().value

                    # if this element is the body, the condition is true
                    # if there was modification the the validity of the condition ...
                    if  scope.getAnswer().hasValidCondition != true
                        #...change the parameter into the answer
                        scope.getAnswer().hasValidCondition = true

                        # try to add a default value
                        if scope.getQuestion() != null && scope.getQuestion().defaultValue != null && scope.getAnswer().value == null
                            scope.getAnswer().value = scope.getQuestion().defaultValue

                            # if this if the first compute, it was caused during the loading :
                            # the question is not edited

                            #if scope.firstComputecondition == true
                            #    scope.firstComputecondition=false
                            #else
                            if scope.$parent.loading == false
                                scope.edited()

                    return true

            #
            # return the name of the user for this question
            # forDataToCompare => return the name of the user for the dataToCompare
            # initialOnly => return initials of the name, or complete name
            #
            scope.getUserName = (forDataToCompare, initialOnly)->
                user = null

                if forDataToCompare == true
                    if scope.displayOldDatas() && scope.getAnswer(true) != null
                        user = scope.$root.getUserByIdentifier(scope.getAnswer(true).lastUpdateUser)
                else
                    if scope.getAnswer() != null
                        user = scope.$root.getUserByIdentifier(scope.getAnswer().lastUpdateUser)

                if user == null
                    return ""
                else if initialOnly
                    initial = user.firstName.substring(0, 1) + user.lastName.substring(0, 1)
                    return initial
                else
                    name = user.firstName + " " + user.lastName
                    return name

            #
            # get the status class
            #
            scope.getStatusClass = ->
                if scope.getAggregation()?
                    return

                #there is no status for document
                if scope.getQuestion() != null && scope.getQuestion().answerType == 'DOCUMENT'
                    return ""

                answer = scope.getAnswer()

                if answer.value != null
                    if answer.wasEdited != undefined && answer.wasEdited == true
                        return 'answer_temp'
                    return 'answer'
                else
                    if answer.wasEdited != undefined && answer.wasEdited == true
                        return 'pending_temp'
                    return 'pending'

            #
            # copy the value of the data to compare to the current value
            #
            scope.copyDataToCompare = ->
                if scope.getAnswer(true) != null
                    scope.getAnswer().value = scope.getAnswer(true).value
                    if scope.getAnswer(true).unitId?
                        scope.getAnswer().unitId = scope.getAnswer(true).unitId
                    if scope.getAnswer(true).comment?
                        scope.getAnswer().comment = scope.getAnswer(true).comment
                    scope.edited()

            #
            # Print the code of the question into the console
            # used when there is a click on the name of the question
            #
            scope.logQuestionCode = ->
                console.log scope.getQuestionCode() + ",value:" + scope.getAnswer().value + ",wasEdited:" + scope.getAnswer().wasEdited
                console.log scope.getAnswer()

            #
            # error message if the user try to enter wrong data into the field
            #
            scope.errorMessage = ""

            scope.getIcon =->
                if scope.ngAggregation?
                    return 'glyphicon-cog'
                if scope.getQuestion()? && scope.getQuestion().answerType == 'DOCUMENT'
                    return 'glyphicon-file'
                return 'glyphicon-share-alt'


            #
            # display a error message before the input
            #
            scope.setErrorMessage = (errorMessage)->
                scope.errorMessage = errorMessage
                if scope.lastTimeOut?
                    $timeout.cancel(scope.lastTimeOut)

                scope.lastTimeOut = $timeout(->
                    scope.errorMessage = ""
                    scope.lastTimeOut = null
                , 2000)

            scope.saveComment = (comment) ->
                scope.getAnswer().comment = comment
                scope.getAnswer().wasEdited = true

            scope.editComment = (canBeEdited=true) ->
                args = {}
                args.comment = scope.getAnswer().comment
                args.save = scope.saveComment
                args.canBeEdited=canBeEdited
                modalService.show(modalService.QUESTION_COMMENT, args)







