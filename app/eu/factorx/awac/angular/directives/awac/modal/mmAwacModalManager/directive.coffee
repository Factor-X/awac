angular
.module('app.directives')
.directive "mmAwacModalManager", (directiveService,$compile) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngCondition: '='
    templateUrl: "$/angular/templates/mm-awac-modal-manager.html"
    replace: true
    link: (scope,element) ->

        # catch event
        scope.$on 'SHOW_MODAL',(event,args) ->
            if args.show == true
                scope.displayModal(args.target,args.params)
            else
                scope.removeModal(args.target)

            return

        # insert modal into html
        scope.displayModal = (target,params) ->


            paramName = 'params_'+target.replace(/-/g,"_")

            scope[paramName] = params
            directive = $compile("<mm-awac-modal-"+target+" ng-params=\""+paramName+"\" ></mm-awac-modal-"+target+">")(scope)
            element.append(directive)

            return


        # remove when the modal is closed
        scope.removeModal = (target)->
            for child in element.children()
                if child.tagName.toLowerCase() == 'mm-awac-modal-'+target.toLowerCase()
                    angular.element(child).remove()

            return
