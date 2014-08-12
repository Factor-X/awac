angular.module('app.controllers').controller("FormCtrl", function($scope, downloadService, $http, messageFlash, modalService, formIdentifier, $timeout, displayFormMenu) {
  $scope.formIdentifier = formIdentifier;
  $scope.displayFormMenu = displayFormMenu;
  $scope.dataToCompare = null;
  $scope.answerList = [];
  $scope.mapRepetition = [];
  /*
  illustration of the structures
  $scope.mapRepetition['A15'] = [{'A15':1},
                                  {'A15':2}]

  $scope.mapRepetition['A16'] = [{'A16':1,'A15':1},
                                 {'A16':2,'A15':1}]
  */
  $scope.loading = true;
  modalService.show(modalService.LOADING);
  downloadService.getJson("answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId, function(data) {
    var answerSave, args, qSet, questionSetDTO, _i, _j, _len, _len2, _ref, _ref2;
    console.log("data");
    console.log(data);
    $scope.o = angular.copy(data);
    if ($scope.o.answersSave !== null && $scope.o.answersSave !== void 0) {
      answerSave = $scope.o.answersSave;
      $scope.answerList = answerSave.listAnswers;
    }
    $scope.loopRepetition = function(questionSetDTO, listQuestionSetRepetition) {
      var answer, child, _i, _j, _len, _len2, _ref, _ref2, _results;
      if (listQuestionSetRepetition == null) {
        listQuestionSetRepetition = [];
      }
      if (questionSetDTO.repetitionAllowed === true) {
        listQuestionSetRepetition[listQuestionSetRepetition.length] = questionSetDTO.code;
        _ref = $scope.answerList;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          answer = _ref[_i];
          if (answer.mapRepetition !== null && answer.mapRepetition !== void 0) {
            if (answer.mapRepetition[questionSetDTO.code] !== null && answer.mapRepetition[questionSetDTO.code] !== void 0 && answer.mapRepetition[questionSetDTO.code] !== 0) {
              $scope.addMapRepetition(questionSetDTO.code, answer.mapRepetition, listQuestionSetRepetition);
            }
          }
        }
      }
      if (questionSetDTO.children) {
        _ref2 = questionSetDTO.children;
        _results = [];
        for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
          child = _ref2[_j];
          _results.push($scope.loopRepetition(child, angular.copy(listQuestionSetRepetition)));
        }
        return _results;
      }
    };
    $scope.addDefaultValue = function(questionSetDTO) {
      var answer, child, question, _i, _j, _k, _len, _len2, _len3, _ref, _ref2, _ref3, _results;
      _ref = questionSetDTO.questions;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        question = _ref[_i];
        if (question.defaultValue !== void 0 && question.defaultValue !== null) {
          _ref2 = $scope.answerList;
          for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
            answer = _ref2[_j];
            if (answer.questionKey === question.code && answer.value === null) {
              answer.value = question.defaultValue;
            }
          }
        }
      }
      _ref3 = questionSetDTO.children;
      _results = [];
      for (_k = 0, _len3 = _ref3.length; _k < _len3; _k++) {
        child = _ref3[_k];
        _results.push($scope.addDefaultValue(child));
      }
      return _results;
    };
    args = {};
    args.time = $scope.o.answersSave.lastUpdateDate;
    $scope.$root.$broadcast("REFRESH_LAST_SAVE_TIME", args);
    _ref = $scope.o.questionSets;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      qSet = _ref[_i];
      $scope.loopRepetition(qSet);
    }
    console.log("$scope.mapRepetition");
    console.log($scope.mapRepetition);
    _ref2 = $scope.o.questionSets;
    for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
      questionSetDTO = _ref2[_j];
      $scope.addDefaultValue(questionSetDTO);
    }
    $timeout(function() {
      modalService.close(modalService.LOADING);
      return $scope.loading = false;
    }, 0);
    return;
  });
  $scope.$on('SAVE_AND_NAV', function(event, args) {
    return $scope.save(args);
  });
  $scope.$on('SAVE', function() {
    return $scope.save();
  });
  $scope.save = function(argToNav) {
    var answer, listAnswerToSave, promise, _i, _len, _ref;
    if (argToNav == null) {
      argToNav = null;
    }
    modalService.show(modalService.LOADING);
    listAnswerToSave = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.wasEdited !== void 0 && answer.wasEdited === true) {
        if (answer.hasValidCondition !== void 0 && answer.hasValidCondition === false) {
          answer.value = null;
        }
        listAnswerToSave[listAnswerToSave.length] = answer;
      }
    }
    console.log("listAnswerToSave");
    console.log(listAnswerToSave);
    if (listAnswerToSave.length === 0) {
      messageFlash.displaySuccess("All answers are already saved !");
      return modalService.close(modalService.LOADING);
    } else {
      $scope.o.answersSave.listAnswers = listAnswerToSave;
      promise = $http({
        method: "POST",
        url: 'answer/save',
        headers: {
          "Content-Type": "application/json"
        },
        data: $scope.o.answersSave
      });
      promise.success(function(data, status, headers, config) {
        var answer, _i, _len, _ref;
        messageFlash.displaySuccess("Your answers are saved !");
        modalService.close(modalService.LOADING);
        _ref = $scope.answerList;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          answer = _ref[_i];
          if (answer.wasEdited !== void 0 && answer.wasEdited === true) {
            answer.wasEdited = false;
          }
        }
        $scope.saveFormProgress();
        if (argToNav !== null) {
          $scope.$root.$broadcast('NAV', argToNav);
        }
        return;
      });
      return promise.error(function(data, status, headers, config) {
        messageFlash.displayError("An error was thrown during the save : " + data.message);
        modalService.close(modalService.LOADING);
        return;
      });
    }
  };
  $scope.getUnitCategories = function(code) {
    var question;
    if ($scope.getQuestion(code) === null) {
      return null;
    }
    question = $scope.getQuestion(code);
    if (question === null || question === void 0) {
      console.log("ERROR : this question was not found : " + code);
      return null;
    }
    if (question.unitCategoryId === null || question.unitCategoryId === void 0) {
      console.log("ERROR : there is no unitCategoryId for this question : " + code);
      return null;
    }
    return $scope.o.unitCategories[question.unitCategoryId];
  };
  $scope.getCodeList = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
    return $scope.o.codeLists[question.codeListName];
  };
  $scope.getRepetitionMapByQuestionSet = function(code, mapRepetition) {
    var listRepetition, repetition, _i, _len, _ref;
    listRepetition = [];
    if ($scope.mapRepetition[code] !== null && $scope.mapRepetition[code] !== void 0) {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if (mapRepetition === null || mapRepetition === void 0 || $scope.compareRepetitionMap(repetition, mapRepetition)) {
          listRepetition[listRepetition.length] = repetition;
        }
      }
    }
    return listRepetition;
  };
  $scope.getQuestion = function(code, listQuestionSets) {
    var q, qSet, result, _i, _j, _len, _len2, _ref;
    if (listQuestionSets == null) {
      listQuestionSets = null;
    }
    if (listQuestionSets === null) {
      if ($scope.o === null || $scope.o === void 0 || $scope.o.questionSets === null) {
        return null;
      }
      listQuestionSets = $scope.o.questionSets;
    }
    if (listQuestionSets) {
      for (_i = 0, _len = listQuestionSets.length; _i < _len; _i++) {
        qSet = listQuestionSets[_i];
        if (qSet.questions) {
          _ref = qSet.questions;
          for (_j = 0, _len2 = _ref.length; _j < _len2; _j++) {
            q = _ref[_j];
            if (q.code === code) {
              return q;
            }
          }
        }
        if (qSet.children) {
          result = $scope.getQuestion(code, qSet.children);
          if (result) {
            return result;
          }
        }
      }
    }
    return null;
  };
  $scope.getAnswerToCompare = function(code, mapIteration) {
    var answer, _i, _len, _ref;
    if ($scope.dataToCompare !== null) {
      _ref = $scope.dataToCompare.answersSave.listAnswers;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        answer = _ref[_i];
        if (answer.questionKey === code) {
          if ($scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
            return answer;
          }
        }
      }
    }
    return null;
  };
  $scope.getAnswerOrCreate = function(code, mapIteration) {
    var answerLine, defaultUnitId, question, result, value, wasEdited;
    if (mapIteration == null) {
      mapIteration = null;
    }
    if (code === null || code === void 0) {
      console.log("ERROR !! getAnswerOrCreate : code is null or undefined");
      return null;
    }
    result = $scope.getAnswer(code, mapIteration);
    if (result) {
      return result;
    } else {
      value = null;
      defaultUnitId = null;
      wasEdited = false;
      if ($scope.getQuestion(code) !== null) {
        question = $scope.getQuestion(code);
        if (question.defaultValue !== null) {
          value = question.defaultValue;
          wasEdited = true;
        }
        if (question.unitCategoryId !== null && question.unitCategoryId !== void 0) {
          defaultUnitId = $scope.getUnitCategories(code).mainUnitId;
        }
      }
      answerLine = {
        'questionKey': code,
        'value': value,
        'unitId': defaultUnitId,
        'mapRepetition': mapIteration,
        'lastUpdateUser': $scope.$root.currentPerson.identifier,
        'wasEdited': wasEdited
      };
      $scope.answerList[$scope.answerList.length] = answerLine;
      return answerLine;
    }
  };
  $scope.getAnswer = function(code, mapIteration) {
    var answer, _i, _len, _ref;
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code) {
        if ($scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
          return answer;
        }
      }
    }
    return null;
  };
  $scope.getListAnswer = function(code, mapIteration) {
    var answer, listAnswer, _i, _len, _ref;
    listAnswer = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code && $scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
        listAnswer[listAnswer.length] = answer;
      }
    }
    return listAnswer;
  };
  $scope.addIteration = function(code, mapRepetition) {
    var max, repetition, repetitionToAdd, _i, _len, _ref;
    max = 0;
    repetitionToAdd = {};
    if (mapRepetition != null) {
      repetitionToAdd = angular.copy(mapRepetition);
    }
    if ($scope.mapRepetition[code] === null || $scope.mapRepetition[code] === void 0) {
      repetitionToAdd[code] = max + 1;
      $scope.mapRepetition[code] = [];
      return $scope.mapRepetition[code][0] = repetitionToAdd;
    } else {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if ($scope.compareRepetitionMap(repetition, mapRepetition) && repetition[code] > max) {
          max = repetition[code];
        }
      }
      repetitionToAdd[code] = max + 1;
      return $scope.mapRepetition[code][$scope.mapRepetition[code].length] = repetitionToAdd;
    }
  };
  $scope.removeIteration = function(questionSetCode, iterationToDelete, mapRepetition) {
    var iteration, key, len, question, _i, _len, _ref, _results;
    len = $scope.answerList.length;
    while (len--) {
      question = $scope.answerList[len];
      if ((question.mapRepetition != null) && $scope.compareRepetitionMap(question.mapRepetition, mapRepetition)) {
        if (question.mapRepetition[questionSetCode] && question.mapRepetition[questionSetCode] === iterationToDelete[questionSetCode]) {
          if ($scope.answerList[len].value !== null) {
            $scope.answerList[len].value = null;
            $scope.answerList[len].wasEdited = true;
          }
        }
      }
    }
    _ref = Object.keys($scope.mapRepetition);
    _results = [];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      _results.push((function() {
        var _results;
        if (key !== '$$hashKey') {
          len = $scope.mapRepetition[key].length;
          _results = [];
          while (len--) {
            iteration = $scope.mapRepetition[key][len];
            _results.push($scope.compareRepetitionMap(iteration, mapRepetition) && iteration[questionSetCode] && iteration[questionSetCode] === iterationToDelete[questionSetCode] ? $scope.mapRepetition[key].splice(len, 1) : void 0);
          }
          return _results;
        }
      })());
    }
    return _results;
  };
  $scope.compareRepetitionMap = function(mapContainer, mapContained) {
    var key, value, _i, _len, _ref;
    if (mapContained === null || mapContained === void 0 || mapContained.length === 0) {
      return true;
    }
    if (mapContainer === null || mapContainer === void 0 || mapContainer.length === 0) {
      return false;
    }
    _ref = Object.keys(mapContained);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if (key !== '$$hashKey') {
        value = mapContained[key];
        if (mapContainer[key] === null || mapContainer[key] === void 0 || mapContainer[key] !== value) {
          return false;
        }
      }
    }
    return true;
  };
  $scope.addMapRepetition = function(questionSetCode, mapRepetitionSource, listQuestionSetRepetition) {
    var founded, key, mapRepetitionToAdd, questionCode, repetition, _i, _j, _k, _len, _len2, _len3, _ref, _ref2;
    mapRepetitionToAdd = {};
    _ref = Object.keys(mapRepetitionSource);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if (key !== '$$hashKey') {
        for (_j = 0, _len2 = listQuestionSetRepetition.length; _j < _len2; _j++) {
          questionCode = listQuestionSetRepetition[_j];
          if (questionCode === key) {
            mapRepetitionToAdd[key] = mapRepetitionSource[key];
          }
        }
      }
    }
    if ($scope.mapRepetition[questionSetCode]) {
      founded = false;
      _ref2 = $scope.mapRepetition[questionSetCode];
      for (_k = 0, _len3 = _ref2.length; _k < _len3; _k++) {
        repetition = _ref2[_k];
        if ($scope.compareRepetitionMap(repetition, mapRepetitionToAdd)) {
          founded = true;
        }
      }
      if (founded === false) {
        return $scope.mapRepetition[questionSetCode][$scope.mapRepetition[questionSetCode].length] = angular.copy(mapRepetitionToAdd);
      }
    } else {
      $scope.mapRepetition[questionSetCode] = [];
      return $scope.mapRepetition[questionSetCode][0] = angular.copy(mapRepetitionToAdd);
    }
  };
  $scope.validNavigation = function() {
    var answer, result, _i, _len, _ref;
    result = {};
    result.modalForConfirm = modalService.CONFIRMATION_EXIT_FORM;
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.wasEdited !== void 0 && answer.wasEdited === true) {
        result.valid = false;
        break;
      }
    }
    return result;
  };
  $scope.$parent.$watch('periodToCompare', function() {
    var promise;
    if ($scope.$parent !== null && $scope.$parent.periodToCompare !== 'default') {
      promise = $http({
        method: "GET",
        url: 'answer/getByForm/' + $scope.formIdentifier + "/" + $scope.$parent.periodToCompare + "/" + $scope.$parent.scopeId,
        headers: {
          "Content-Type": "application/json"
        }
      });
      promise.success(function(data, status, headers, config) {
        $scope.dataToCompare = data;
        return;
      });
      return promise.error(function(data, status, headers, config) {
        return;
      });
    } else {
      return $scope.dataToCompare = null;
    }
  });
  return $scope.saveFormProgress = function() {
    var answer, answered, formProgress, formProgressDTO, founded, listTotal, percentage, promise, total, _i, _j, _len, _len2, _ref, _ref2;
    percentage = 0;
    total = 0;
    answered = 0;
    listTotal = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if ($scope.getQuestion(answer.questionKey).answerType !== 'DOCUMENT') {
        if (answer.hasValidCondition === void 0 || answer.hasValidCondition === null || answer.hasValidCondition === true) {
          total++;
          listTotal[listTotal.length] = answer;
          if (answer.value !== null) {
            answered++;
          }
        }
      }
    }
    percentage = answered / total * 100;
    percentage = Math.floor(percentage);
    console.log("PROGRESS : " + answered + "/" + total + "=" + percentage);
    console.log(listTotal);
    formProgressDTO = {};
    formProgressDTO.form = $scope.formIdentifier;
    formProgressDTO.period = $scope.$parent.periodKey;
    formProgressDTO.scope = $scope.$parent.scopeId;
    formProgressDTO.percentage = percentage;
    founded = false;
    _ref2 = $scope.$parent.formProgress;
    for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
      formProgress = _ref2[_j];
      console.log(formProgress.form + "-" + $scope.formIdentifier);
      if (formProgress.form === $scope.formIdentifier) {
        founded = true;
        formProgress.percentage = percentage;
      }
    }
    if (founded === false) {
      $scope.$parent.formProgress[$scope.$parent.formProgress.length] = formProgressDTO;
    }
    promise = $http({
      method: "POST",
      url: 'answer/formProgress',
      headers: {
        "Content-Type": "application/json"
      },
      data: formProgressDTO
    });
    return promise.success(function(data, status, headers, config) {
      return;
    });
  };
});