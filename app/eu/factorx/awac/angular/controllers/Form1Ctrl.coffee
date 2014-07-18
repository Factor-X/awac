angular
.module('app.controllers')
.controller "Form1Ctrl", ($scope, downloadService, $http) ->
    $scope.formIdentifier = "TAB1"

    #this variable contains all answer, new and old
    $scope.answerList=[]

    console.log $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId
    downloadService.getJson "answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, (data) ->

        $scope.o = data

        #build the list of answers
        $scope.scanQuestionSet = (questionSetAnswerDTO, mapRepetition) ->

          #add repetition into the mapRepetition
          if questionSetAnswerDTO.repetitionIndex !=null
            mapRepetition[questionSetAnswerDTO.questionSetCode] =questionSetAnswerDTO.repetitionIndex

          #create question
          for q in questionSetAnswerDTO.questions
            $scope.answerList[$scope.answerList.length] = {
              'questionKey' : q.questionKey
              'value' : q.value
              'unitId' : q.unitId
              'mapRepetition' : mapRepetition
            }

          #loop in the children
          for c in questionSetAnswerDTO.children
            $scope.scanQuestionSet(c, mapRepetition)



        # getAnswerByQuestionCode
        $scope.getAnswer = (code) ->
          listAnswer=[]
          for answer in $scope.answerList
            #control the code
            if answer.questionKey == code
              listAnswer[listAnswer.length] = answer

          return listAnswer


        # getAnswerByQuestionCode and mapIteration
        $scope.getAnswer = (code, mapIteration) ->
            for answer in $scope.answerList
                #control the code
                if answer.questionKey == code

                    #control the repetition map
                    failed=false
                    for iParam in mapIteration
                      if answer.mapRepetition[iParam.key]==null || answer.mapRepetition[iParam.key] != iParam.value
                        failed = true

                    if failed == false
                      return answer

            return null

        # getUnitsByQuestionCode
        $scope.U = (code) ->
            unitCategoryId = null;
            for q in $scope.o.questions
                if q.questionKey == code
                    unitCategoryId = q.unitCategoryId

            if unitCategoryId == null
                console.error "impossible to find question by its code: " + code
                return null

            for uc in $scope.o.unitCategories
                if uc.id == unitCategoryId
                    return uc.units

            console.error "impossible to find unit category by its id: " + unitCategoryId + " question code was: " + code

            return null

        # getOptionsByQuestionCode
        $scope.O = (code) ->
            codeListName = null;
            for q in $scope.o.questions
                if q.questionKey == code
                    codeListName = q.codeListName

            if codeListName == null
                console.error "impossible to find question by its code: " + code
                return null

            for cl in $scope.o.codeLists
                if cl.code == codeListName
                    return cl.codeLabels

            console.error "impossible to find codeList by its code: " + codeLabelName + " question code was: " + code

            return null

    $scope.$on 'SAVE', () ->
        promise = $http
            method: "POST"
            url: 'answer/save'
            headers:
                "Content-Type": "application/json"
            data: $scope.o.answersSaveDTO

        promise.success (data, status, headers, config) ->
            console.log "SAVE !"
            return

        promise.error (data, status, headers, config) ->
            console.log "ERROR : " + data.message
            return
