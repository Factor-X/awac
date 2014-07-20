angular
.module('app.controllers')
.controller "Form2Ctrl", ($scope, downloadService, $http) ->
    $scope.formIdentifier = "TAB2"

    #this variable contains all answer, new and old
    $scope.answerList=[]
    $scope.mapRepetition=[]
    $scope.loading = true

    downloadService.getJson "answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, (data) ->

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

        console.log "$scope.mapRepetition"
        console.log $scope.mapRepetition

        #temp
        ###
        $scope.mapRepetition['A15'] = [{'A15':1},
                                        {'A15':2}]

        $scope.mapRepetition['A16'] = [{'A16':1,'A15':1},
                                       {'A16':2,'A15':1}]
        ###

      $scope.loopRepetition = (questionSetDTO, currentRepetition=null) ->

        if questionSetDTO.repetitionAllowed == true

          #find if the answer are already repeated on this repetition
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
                repetitionNumber = answer.mapRepetition[questionSetDTO.code]
                code= questionSetDTO.code
                repetitionToAdd = {}#code:repetition}
                repetitionToAdd[questionSetDTO.code] =repetitionNumber
                if $scope.mapRepetition[questionSetDTO.code]
                  founded=false
                  for repetition in $scope.mapRepetition[questionSetDTO.code]
                    if repetition[questionSetDTO.code] == repetitionNumber
                      console.log "exite dajà"
                      founded=true
                  if founded == false
                    console.log "existe mais ajouté"
                    $scope.mapRepetition[questionSetDTO.code][$scope.mapRepetition[questionSetDTO.code].length] =repetitionToAdd
                else
                  console.log "exite pas, ajoute"
                  $scope.mapRepetition[questionSetDTO.code] = []
                  $scope.mapRepetition[questionSetDTO.code][0] = repetitionToAdd


              ###
                repetition = answer.mapRepetition[questionSetDTO.code]
                #try to add this repetition to the mapRepetition
                if mapRepetition[questionSetDTO.code] !=null
                  if mapRepetition.get[questionSetDTO.code]['repetition']
                    mapRepetition.get[mapRepetition.get.length] = repetition

                else mapRepetition[mapRepetition.length] = [repetition]
              ###

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

    $scope.getRepetitionMapByQuestionSet = (code) ->
      return $scope.mapRepetition[code]

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

    $scope.getAnswerOrCreate = (code, mapIteration =null) ->
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

    # getAnswerByQuestionCode and mapIteration
    $scope.getAnswer = (code, mapIteration) ->
      for answer in $scope.answerList
        #control the code
        if answer.questionKey == code

          #control the repetition map
          failed=false
          if mapIteration
            for key in Object.keys(mapIteration)
              if key != '$$hashKey'
                value = mapIteration[key]
                if answer.mapRepetition[key]==null || answer.mapRepetition[key] != value
                  failed = true

          if failed == false
            return answer

      return null

    $scope.getListAnswer = (code, mapIteration) ->

      listAnswer = []

      for answer in $scope.answerList
        #control the code
        if answer.questionKey == code
          listAnswer[listAnswer.length] = answer

      return listAnswer

    $scope.addIteration = (code, mapRepetition) ->
      #TODO implement mapRepetition
      max = 1
      repetitionToAdd = {}

      if $scope.mapRepetition[code] == null
        repetitionToAdd[code] =max
        $scope.mapRepetition[code] = []
        $scope.mapRepetition[code][0] = repetitionToAdd
      else
        for repetition in $scope.mapRepetition[code]
          if repetition[code] > max
            max = repetition[code]

        repetitionToAdd[code] =max+1
        $scope.mapRepetition[code][$scope.mapRepetition[code].length] = repetitionToAdd

    $scope.removeIteration = (questionSetCode,iterationToDelete) ->

      #delete question
      len = $scope.answerList.length
      while (len--)
        question = $scope.answerList[len]
        if question.mapRepetition!=null
          if question.mapRepetition[questionSetCode] && question.mapRepetition[questionSetCode]==iterationToDelete[questionSetCode]
            $scope.answerList.splice(len,1)

      #delete iteration
      if $scope.mapRepetition[questionSetCode]
        len = $scope.mapRepetition[questionSetCode].length
        while (len--)
          iteration = $scope.mapRepetition[questionSetCode][len]
          if iteration[questionSetCode] && iteration[questionSetCode] == iterationToDelete[questionSetCode]
            $scope.mapRepetition[questionSetCode].splice(len,1)



