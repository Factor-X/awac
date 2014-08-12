angular.module('app.directives').directive("mmAwacQuestion", function(directiveService, translationService, $compile, $timeout, modalService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '=',
      ngCondition: '=',
      ngRepetitionMap: '=',
      ngAggregation: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-question.html",
    replace: true,
    compile: function() {
      return {
        post: function(scope, element) {
          directiveService.autoScopeImpl(scope);
          scope.$watch('ngAggregation', function() {
            if (scope.getAggregation() != null) {
              return scope.getAnswer().value = scope.getAggregation();
            }
          });
          scope.$watch('$parent.o', function() {
            scope.getTemplate(true);
            return scope.getTemplate(false);
          });
          scope.getTemplate = function(dataToCompare) {
            var answerType, directive, directiveName, isAggregation, toCompare;
            if ($('.inject-data:first', element).html() === '') {
              toCompare = "";
              isAggregation = "false";
              directiveName = "";
              if (dataToCompare === true) {
                toCompare = "true";
              } else {
                toCompare = "false";
              }
              if (scope.getAggregation() != null) {
                isAggregation = "true";
              }
              if (scope.getQuestion() !== null) {
                answerType = scope.getQuestion().answerType;
                if (answerType === 'BOOLEAN') {
                  directiveName = "boolean-question";
                } else if (answerType === 'INTEGER') {
                  directiveName = "integer-question";
                } else if (answerType === 'DOUBLE') {
                  if (scope.getQuestion().unitCategoryId !== null || scope.getQuestion().unitCategoryId !== void 0) {
                    directiveName = "real-with-unit-question";
                  } else {
                    directiveName = "real-question";
                  }
                } else if (answerType === 'PERCENTAGE') {
                  directiveName = "percentage-question";
                } else if (answerType === 'STRING') {
                  directiveName = "string-question";
                } else if (answerType === 'VALUE_SELECTION') {
                  directiveName = "select-question";
                } else if (answerType === 'DOCUMENT') {
                  directiveName = "document-question";
                }
                directive = $compile("<mm-awac-" + directiveName + " ng-data-to-compare=\"" + toCompare + "\" ng-is-aggregation=\"" + isAggregation + "\"></mm-awac-" + directiveName + ">")(scope);
                if (dataToCompare === true) {
                  return $('.inject-data-to-compare:first', element).append(directive);
                } else {
                  return $('.inject-data:first', element).append(directive);
                }
              }
            }
          };
          scope.getQuestion = function() {
            return scope.$parent.getQuestion(scope.getQuestionCode());
          };
          scope.hasDescription = function() {
            return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
          };
          scope.getAnswer = function(forDataToCompare) {
            if (forDataToCompare == null) {
              forDataToCompare = false;
            }
            if (forDataToCompare) {
              return scope.$parent.getAnswerToCompare(scope.getQuestionCode(), scope.getRepetitionMap());
            } else {
              if ((scope.getAggregation() != null) && scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap()).value === null) {
                scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap()).value = scope.getAggregation();
              }
              return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap());
            }
          };
          scope.getUnitCategories = function() {
            return scope.$parent.getUnitCategories(scope.getQuestionCode());
          };
          scope.getCodeList = function() {
            return scope.$parent.getCodeList(scope.getQuestionCode());
          };
          scope.displayOldDatas = function() {
            if (scope.$parent.dataToCompare === null) {
              return false;
            }
            return true;
          };
          scope.edited = function() {
            if (scope.getAggregation() != null) {
              return;
            } else {
              if (scope.getAnswer().value !== null) {
                if (scope.getAnswer().value.length === 0) {
                  scope.getAnswer().value = null;
                }
              }
              scope.getAnswer().wasEdited = true;
              return scope.getAnswer().lastUpdateUser = scope.$root.currentPerson.identifier;
            }
          };
          scope.$watch('ngCondition', function(n, o) {
            if (n !== o) {
              return scope.$root.$broadcast('CONDITION');
            }
          });
          scope.$on('CONDITION', function(event, args) {
            return scope.testVisibility(element);
          });
          scope.firstComputecondition = true;
          scope.testVisibility = function(elementToTest) {
            if (elementToTest.hasClass('condition-false') === true || ((scope.getCondition() != null) && scope.getCondition() === false)) {
              if (scope.getAnswer().hasValidCondition !== false) {
                scope.getAnswer().hasValidCondition = false;
                if (scope.getAnswer().value !== null) {
                  scope.getAnswer().value = null;
                  if (scope.$parent.loading === false) {
                    scope.edited();
                    scope.$root.$broadcast('CONDITION');
                  } else {
                    scope.getAnswer().wasEdited = false;
                  }
                }
              }
              return false;
            }
            if (elementToTest.parent()[0].tagName !== 'BODY') {
              return scope.testVisibility(elementToTest.parent());
            } else {
              if (scope.getAnswer().hasValidCondition !== true) {
                scope.getAnswer().hasValidCondition = true;
                if (scope.getQuestion() !== null && scope.getQuestion().defaultValue !== null && scope.getAnswer().value === null) {
                  scope.getAnswer().value = scope.getQuestion().defaultValue;
                  if (scope.$parent.loading === false) {
                    scope.edited();
                  }
                }
              }
              return true;
            }
          };
          scope.getUserName = function(forDataToCompare, initialOnly) {
            var initial, name, user;
            user = null;
            if (forDataToCompare === true) {
              if (scope.displayOldDatas() && scope.getAnswer(true) !== null) {
                user = scope.$root.getUserByIdentifier(scope.getAnswer(true).lastUpdateUser);
              }
            } else {
              if (scope.getAnswer() !== null) {
                user = scope.$root.getUserByIdentifier(scope.getAnswer().lastUpdateUser);
              }
            }
            if (user === null) {
              return "";
            } else if (initialOnly) {
              initial = user.firstName.substring(0, 1) + user.lastName.substring(0, 1);
              return initial;
            } else {
              name = user.firstName + " " + user.lastName;
              return name;
            }
          };
          scope.getStatusClass = function() {
            var answer;
            if (scope.getAggregation() != null) {
              return;
            }
            if (scope.getQuestion() !== null && scope.getQuestion().answerType === 'DOCUMENT') {
              return "";
            }
            answer = scope.getAnswer();
            if (answer.value !== null) {
              if (answer.wasEdited !== void 0 && answer.wasEdited === true) {
                return 'answer_temp';
              }
              return 'answer';
            } else {
              if (answer.wasEdited !== void 0 && answer.wasEdited === true) {
                return 'pending_temp';
              }
              return 'pending';
            }
          };
          scope.copyDataToCompare = function() {
            if (scope.getAnswer(true) !== null) {
              scope.getAnswer().value = scope.getAnswer(true).value;
              if (scope.getAnswer(true).unitId != null) {
                scope.getAnswer().unitId = scope.getAnswer(true).unitId;
              }
              if (scope.getAnswer(true).comment != null) {
                scope.getAnswer().comment = scope.getAnswer(true).comment;
              }
              return scope.edited();
            }
          };
          scope.logQuestionCode = function() {
            return console.log(scope.getQuestionCode() + ",value:" + scope.getAnswer().value + ",wasEdited:" + scope.getAnswer().wasEdited);
          };
          scope.errorMessage = "";
          scope.setErrorMessage = function(errorMessage) {
            scope.errorMessage = errorMessage;
            if (scope.lastTimeOut != null) {
              $timeout.cancel(scope.lastTimeOut);
            }
            return scope.lastTimeOut = $timeout(function() {
              scope.errorMessage = "";
              return scope.lastTimeOut = null;
            }, 2000);
          };
          scope.saveComment = function(comment) {
            scope.getAnswer().comment = comment;
            return scope.getAnswer().wasEdited = true;
          };
          return scope.editComment = function(canBeEdited) {
            var args;
            if (canBeEdited == null) {
              canBeEdited = true;
            }
            args = {};
            args.comment = scope.getAnswer().comment;
            args.save = scope.saveComment;
            args.canBeEdited = canBeEdited;
            return modalService.show(modalService.QUESTION_COMMENT, args);
          };
        }
      };
    }
  };
});