angular
.module('app.controllers')
.controller "Form6Ctrl", ($scope, downloadService, $http) ->
    $scope.formIdentifier = "TAB6"



    #this variable contains all answer, new and old
    $scope.answerList=[]
    $scope.mapRepetition=[]
    ###
    illustration of the structures
    $scope.mapRepetition['A15'] = [{'A15':1},
                                    {'A15':2}]

    $scope.mapRepetition['A16'] = [{'A16':1,'A15':1},
                                   {'A16':2,'A15':1}]
    ###

    $scope.loading = true

    downloadService.getJson "answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, (data) ->


      console.log "data"
      console.log data
      $scope.o = data

      #build the list of answers
      $scope.storeAnswers = () ->
        console.log "je suis $scope.storeAnswers"
        #recove answerSave
        console.log "$scope.o"
        console.log $scope.o
        answerSave = $scope.o.answersSave

        #save answer
        $scope.answerList =  answerSave.listAnswers
        console.log "$scope.answerList"
        console.log $scope.answerList

        #build list of repetition for the mmAwacRepetition
        for qSet in $scope.o.questionSets
          $scope.loopRepetition(qSet)

        #TEMP
        #$scope.mapRepetition['A244'] = [{'A244':10},{'A244':20}]

        #$scope.mapRepetition['A273'] = [{'A244':10,'A273':1},{'A244':20,'A273':1}]

        console.log "$scope.mapRepetition"
        console.log $scope.mapRepetition

      $scope.loopRepetition = (questionSetDTO) ->
        #TODO implement mapRepetition

        if questionSetDTO.repetitionAllowed == true

          #find if the answer are already repeated on this repetition
          if questionSetDTO.questions
            for q in questionSetDTO.questions
              #recover answer
              listAnswer = $scope.getListAnswer(q.code)

              for answer in listAnswer

                console.log "answer in $scope.loopRepetition"
                console.log answer

                #control if the answer have a repetition for this questionSetDTO
                if answer.mapRepetition==null
                  #this is an error
                  console.log("mapRepetition expected but not found")
                else
                  ###
                  repetitionNumber = answer.mapRepetition[questionSetDTO.code]
                  code= questionSetDTO.code
                  repetitionToAdd = {}#code:repetition}
                  repetitionToAdd[questionSetDTO.code] =repetitionNumber
                  ###
                  if $scope.mapRepetition[questionSetDTO.code]
                    founded=false
                    for repetition in $scope.mapRepetition[questionSetDTO.code]
                      if $scope.compareRepetitionMap(repetition,answer.mapRepetition)
                        founded=true
                    if founded == false
                      $scope.mapRepetition[questionSetDTO.code][$scope.mapRepetition[questionSetDTO.code].length] = angular.copy(answer.mapRepetition)
                  else
                    $scope.mapRepetition[questionSetDTO.code] = []
                    $scope.mapRepetition[questionSetDTO.code][0] = angular.copy(answer.mapRepetition)

        if questionSetDTO.children
          for child in questionSetDTO.children
            $scope.loopRepetition(child)


      $scope.storeAnswers()
      $scope.loading =false

    #
    # save the result of this form
    #
    $scope.$on 'SAVE', () ->

      #build the list to save
      listAnswerToSave=[]
      for answer in $scope.answerList
        if answer.value # && answer.visible
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
        console.log "SAVE !"
        return

      promise.error (data, status, headers, config) ->
        console.log "ERROR : " + data.message
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
        console.log "ERROR : this question was not found : "+code
        return null
      if question.unitCategoryId == null || question.unitCategoryId == undefined
        console.log "ERROR : there is no unitCategoryId for this question : "+code
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
      if $scope.mapRepetition[code]!=null && $scope.mapRepetition[code]!=undefined
        for repetition in $scope.mapRepetition[code]

          #control map
          if mapRepetition == null || mapRepetition == undefined || $scope.compareRepetitionMap(repetition, mapRepetition)
            listRepetition[listRepetition.length] = repetition

      return listRepetition

    #
    # getQuestionByCode
    #
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

    #
    # get the answer by code and mapIteration
    # if there is not answer for this case, create it
    #
    $scope.getAnswerOrCreate = (code, mapIteration) ->
      result = $scope.getAnswer(code, mapIteration)
      if result
        return result
      else
        #if the answer was not founded, create it
        answerLine = {
          'questionKey':code
          'value':null
          'unitId':null
          'mapRepetition':mapIteration
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
        console.log "mapRepetition"
        console.log mapRepetition
        repetitionToAdd = angular.copy(mapRepetition)

      if $scope.mapRepetition[code] == null || $scope.mapRepetition[code] == undefined
        #there is no repetition for the code => create a new iteration
        repetitionToAdd[code] =max+1
        $scope.mapRepetition[code] = []
        $scope.mapRepetition[code][0] = repetitionToAdd
      else
        for repetition in $scope.mapRepetition[code]
          if $scope.compareRepetitionMap(repetition,mapRepetition) && repetition[code] > max
            max = repetition[code]

        repetitionToAdd[code] =max+1
        $scope.mapRepetition[code][$scope.mapRepetition[code].length] = repetitionToAdd

    #
    # remove an iteration by question set code, number of the iteration and the mapRepetition
    #
    $scope.removeIteration = (questionSetCode,iterationToDelete,mapRepetition) ->

      #delete question
      len = $scope.answerList.length
      while (len--)
        question = $scope.answerList[len]
        if question.mapRepetition!=null && question.mapRepetition!=undefined && $scope.compareRepetitionMap(question.mapRepetition,mapRepetition)
          if question.mapRepetition[questionSetCode] && question.mapRepetition[questionSetCode]==iterationToDelete[questionSetCode]
            $scope.answerList.splice(len,1)

      # delete iteration
      # check all iteration because it must remove the iteration linked to the iteration to delete
      for key in Object.keys($scope.mapRepetition)
        if key != '$$hashKey'
          #value = mapContained[key]
          len = $scope.mapRepetition[key].length
          while (len--)
            iteration = $scope.mapRepetition[key][len]
            if $scope.compareRepetitionMap(iteration,mapRepetition) && iteration[questionSetCode] && iteration[questionSetCode] == iterationToDelete[questionSetCode]
              $scope.mapRepetition[key].splice(len,1)

    #
    # compare to mapRepetition
    # if the mapContained is null or undefined, the result is true
    # if all items of the second are included into the first, return true
    #
    $scope.compareRepetitionMap = (mapContainer, mapContained) ->
      if mapContained == null || mapContained == undefined
        return true
      for key in Object.keys(mapContained)
        if key != '$$hashKey'
          value = mapContained[key]
          if mapContainer[key]==null || mapContainer[key]==undefined || mapContainer[key]!=value
            return false
      return true
