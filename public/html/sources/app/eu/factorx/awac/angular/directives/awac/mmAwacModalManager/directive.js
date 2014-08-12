angular.module('app.directives').directive("mmAwacModalManager", function(directiveService, $compile) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngCondition: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-manager.html",
    replace: true,
    link: function(scope, element) {
      scope.$on('SHOW_MODAL', function(event, args) {
        if (args.show === true) {
          return scope.displayModal(args.target, args.params);
        } else {
          return scope.removeModal(args.target);
        }
      });
      scope.displayModal = function(target, params) {
        var directive, paramName;
        paramName = 'params_' + target.replace(/-/g, "_");
        scope[paramName] = params;
        directive = $compile("<mm-awac-modal-" + target + " ng-params=\"" + paramName + "\" ></mm-awac-modal-" + target + ">")(scope);
        return element.append(directive);
      };
      return scope.removeModal = function(target) {
        var child, _i, _len, _ref, _results;
        _ref = element.children();
        _results = [];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          child = _ref[_i];
          _results.push(child.tagName.toLowerCase() === 'mm-awac-modal-' + target.toLowerCase() ? child.remove() : void 0);
        }
        return _results;
      };
    }
  };
});