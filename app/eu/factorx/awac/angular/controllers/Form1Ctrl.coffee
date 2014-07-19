angular
.module('app.controllers')
.controller "Form1Ctrl", ($scope, downloadService, $http) ->
    $scope.formIdentifier = "TAB1"

    #this variable contains all answer, new and old
    $scope.answerList=[]
    $scope.loading = true

    console.log $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId
    downloadService.getJson "answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, (data) ->

        $scope.o = data

        #build the list of answers
        $scope.storeAnswers = () ->

          #recove answerSave
          console.log "$scope.o"
          console.log $scope.o
          answerSave = $scope.o.answersSave

          #save answer
          $scope.answerList =  answerSave.listAnswers
          console.log "$scope.answerList"
          console.log $scope.answerList

          #build list of repetition for the mmAwacRepetition

        $scope.storeAnswers()
        $scope.loading =false



        ###

        $scope.mapRepetition=[]

        $scope.loopRepetition = (questionSetDTO, currentRepetition) ->
          if questionSetDTO.repetitionAllowed

            #find if the answer are already repeated on this repetition
            for q in questionSetDTO.questions
              #recover answer
              answer = $scope.getAnswer q.code

              #control if the answer have a repetition for this questionSetDTO
              if answer.mapRepetition.length == 0
                #this is an error
                console.log("mapRepetition expected but not found")
              else if answer.mapRepetition[questionSetDTO.code]!=null
                repetition = answer.mapRepetition[questionSetDTO.code]
                #try to add this repetition to the mapRepetition
                if mapRepetition[questionSetDTO.code] !=null
                  if mapRepetition.get[questionSetDTO.code].get[repetition]
                    mapRepetition.get[mapRepetition.get.length] = repetition

                else mapRepetition[mapRepetition.length] = [repetition]

        #scan the questionSet
        $scope.scanQuestionSet()


        $scope.getQuestionSet = (code) ->
          return $scope.getQuestionSet($scope.o.questionSets, code)

        $scope.getQuestionSet = (qSet, code) ->
          for qSet in qSet.children
            if qSet.code == code
              return qSet
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
        ###
    $scope.$on 'SAVE', () ->


      #build the list to save
      listAnswerToSave=[]
      for answer in $scope.answerList
        if answer.value # && answer.visible
          listAnswerToSave[listAnswerToSave.length] = answer

      #and replace the list
      $scope.o.answersSave.listAnswers = listAnswerToSave

      console.log "$scope.o.answersSave.listAnswers"
      console.log $scope.o.answersSave.listAnswers


      promise = $http
          method: "POST"
          url: 'answer/save'
          headers:
              "Content-Type": "application/json"
          data: $scope.o.answersSave

      promise.success (data, status, headers, config) ->
          console.log "SAVE !"
          return

      promise.error (data, status, headers, config) ->
          console.log "ERROR : " + data.message
          return

    #get list choice by question code
    $scope.getUnitCategories = (code) ->
      if $scope.loading
        return null

      #recover the question
      question = $scope.getQuestion(code)
      #recover the list
      return $scope.o.unitCategories[question.unitCategoryId]

    #get list choice by question code
    $scope.getCodeList = (code) ->
      if $scope.loading
        return null

      #recover the question
      question = $scope.getQuestion(code)
      #recover the list
      return $scope.o.codeLists[question.codeListName]

    # getQuestionByCode
    $scope.getQuestion = (code,listQuestionSets=$scope.o.questionSets) ->
      if listQuestionSets
        for qSet in listQuestionSets
          if qSet.questions
            for q in qSet.questions
              if q.code == code
                return q
          if qSet.children
            result = $scope.getQuestion(code,qSet.children)
            if result
              return result
      return null

    # getAnswerByQuestionCode and mapIteration
    $scope.getAnswer = (code, mapIteration =null) ->
      for answer in $scope.answerList
        #control the code
        if answer.questionKey == code

          #control the repetition map
          failed=false
          if mapIteration
            for iParam in mapIteration
              if answer.mapRepetition[iParam.key]==null || answer.mapRepetition[iParam.key] != iParam.value
                failed = true

          if failed == false
            return answer

      #if the answer was not founded, create it
      answerLine = {
        'questionKey':code
        'value':null
        'unitId':null
        'mapRepetition':mapIteration
      }
      $scope.answerList[$scope.answerList.length] = answerLine
      return answerLine
