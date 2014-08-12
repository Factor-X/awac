angular.module('app.directives').directive("mmAwacRepetitionQuestionDisabled", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '=',
      ngIteration: '=',
      ngRepetitionMap: '=',
      ngCondition: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-repetition-question-disabled.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getQuestionSet = function() {
        return scope.$parent.getQuestionSet(scope.getQuestionCode());
      };
      scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
      scope.removeAnwser = function() {
        return scope.$parent.removeIteration(scope.getQuestionCode(), scope.getIteration(), scope.getRepetitionMap());
      };
      scope.$watch('ngCondition', function() {
        return scope.$root.$broadcast('CONDITION');
      });
      scope.addIteration = function() {
        return scope.$parent.addIteration(scope.getQuestionCode(), scope.getRepetitionMap());
      };
      return scope.getRepetitionMapByQuestionSet = function() {
        return scope.$parent.getRepetitionMapByQuestionSet(scope.getQuestionCode(), scope.getRepetitionMap());
      };
    }
  };
});