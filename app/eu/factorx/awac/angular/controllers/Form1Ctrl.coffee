angular
.module('app.controllers')
.controller "Form1Ctrl", ($scope, downloadService, $http) ->
    $scope.formIdentifier = "TAB1"

    #this variable contains all answer, new and old
    $scope.answerList=[]
    $scope.loading = true

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

          #build list of repetition for the mmAwacRepetition

        $scope.storeAnswers()
        $scope.loading =false

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
