angular
.module('app.directives')
.directive "mmAwacSection", (directiveService, downloadService,messageFlash) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngTitleCode: '='
        ngMode: '='
    templateUrl: "$/angular/templates/mm-awac-section.html"
    replace: true
    transclude: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.decorateMode = (v) ->
            if v
                return 'element_' + v
            else
                return 'element_table'

        scope.lock = ->

            if (not scope.isLocked() or scope.isLockedByMyself()  or  scope.$root.currentPerson.isAdmin) and not scope.isValidate()

                data = {}
                data.questionSetKey = scope.getTitleCode()
                data.periodCode = scope.$root.periodSelectedKey
                data.scopeId = scope.$root.scopeSelectedId
                data.lock = if scope.isLocked() then false else true

                downloadService.postJson '/awac/answer/lockQuestionSet',data, (result) ->
                    if result.success
                        scope.$parent.lockQuestionSet(scope.getTitleCode(), data.lock)
                    else
                        messageFlash.displayError(result.data.message)

        scope.isLockedByMyself = ->
            if scope.$parent.getQuestionSetLocker(scope.getTitleCode())?
                if scope.$parent.getQuestionSetLocker(scope.getTitleCode()).identifier == scope.$root.currentPerson.identifier
                    return true
            return false

        scope.isLocked = ->
            if scope.$parent.getQuestionSetLocker(scope.getTitleCode())?
                return true
            return false

        scope.getLocker = ->
            return scope.$parent.getQuestionSetLocker(scope.getTitleCode())

        scope.getLockClass = ->
            if scope.isLocked()
                if (scope.isLockedByMyself()  || scope.$root.currentPerson.isAdmin) && scope.isValidate() == false
                    return 'lock_close_by_myself'
                return 'lock_close'
            else if scope.isValidate()
                return 'lock_open_disabled'
            else
                return 'lock_open'

        scope.valide = ->

            if not scope.isValidate() or scope.isValidateByMyself() or  scope.$root.currentPerson.isAdmin

                data = {}
                data.questionSetKey = scope.getTitleCode()
                data.periodCode = scope.$root.periodSelectedKey
                data.scopeId = scope.$root.scopeSelectedId
                data.lock = if scope.isValidate() then false else true

                downloadService.postJson '/awac/answer/validateQuestionSet',data, (result) ->
                    if result.success
                        scope.$parent.validateQuestionSet(scope.getTitleCode(), data.lock)
                    else
                        messageFlash.displayError(result.data.message)


        scope.isValidateByMyself = ->
            if scope.$parent.getQuestionSetValidator(scope.getTitleCode())?
                if scope.$parent.getQuestionSetValidator(scope.getTitleCode()).identifier == scope.$root.currentPerson.identifier
                    return true
            return false

        scope.isValidate = ->
            if scope.$parent.getQuestionSetValidator(scope.getTitleCode())?
                return true
            return false

        scope.getValidator = ->
            return scope.$parent.getQuestionSetValidator(scope.getTitleCode())


        scope.getValidateClass = ->
            if scope.isValidate()
                if scope.isValidateByMyself() || scope.$root.currentPerson.isAdmin
                    return 'validate_by_myself'
                return 'validated'
            else
                return 'validate_no_valid'







