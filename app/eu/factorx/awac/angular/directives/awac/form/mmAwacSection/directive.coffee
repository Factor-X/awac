angular
.module('app.directives')
.directive "mmAwacSection", (directiveService, downloadService, messageFlash, modalService, $location) ->
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
            if (not scope.isLocked() or scope.isLockedByMyself() or scope.$root.currentPerson.isAdmin) and not scope.isValidate() and scope.$root.closedForms == false

                data = {}
                data.questionSetKey = scope.getTitleCode()
                data.periodCode = scope.$root.periodSelectedKey
                data.scopeId = scope.$root.scopeSelectedId
                data.lock = if scope.isLocked() then false else true

                downloadService.postJson '/awac/answer/lockQuestionSet', data, (result) ->
                    if result.success
                        scope.$parent.lockQuestionSet(scope.getTitleCode(), data.lock)

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
                if (scope.isLockedByMyself() || scope.$root.currentPerson.isAdmin) and scope.isValidate() == false and scope.$root.closedForms == false
                    return 'lock_close_by_myself'
                return 'lock_close'
            else if scope.isValidate()
                return 'lock_open_disabled'
            else
                return 'lock_open'

        scope.valide = ->


            if scope.$root.closedForms == false
                if not scope.isValidate() or scope.isValidateByMyself() or scope.$root.currentPerson.isAdmin
                    if not scope.$root.verificationRequest || (scope.$root.verificationRequest.status=='VERIFICATION_STATUS_CORRECTION' && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status == 'rejected')

                        data = {}
                        data.questionSetKey = scope.getTitleCode()
                        data.periodCode = scope.$root.periodSelectedKey
                        data.scopeId = scope.$root.scopeSelectedId
                        data.lock = if scope.isValidate() then false else true

                        downloadService.postJson '/awac/answer/validateQuestionSet', data, (result) ->
                            if result.success
                                scope.$parent.validateQuestionSet(scope.getTitleCode(), data.lock)
                                scope.$root.testCloseable()


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

        scope.getValidatorName = ->
            if scope.getValidator()?
                scope.getValidator()
                return scope.getValidator().firstName + " " + scope.getValidator().lastName

        scope.getLockerName = ->
            if scope.getLocker()?
                return scope.getLocker().firstName + " " + scope.getLocker().lastName

        scope.getValidateClass = ->
            if scope.isValidate()
                if scope.$root.closedForms == false
                    if scope.isValidateByMyself() or scope.$root.currentPerson.isAdmin
                        if not scope.$root.verificationRequest || (scope.$root.verificationRequest.status=='VERIFICATION_STATUS_CORRECTION' && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status == 'rejected')
                            return 'validate_by_myself'
                return 'validated'
            else
                return 'validate_no_valid'

        scope.verification = (valid) ->
            data =
                questionSetKey: scope.getTitleCode()
                periodKey: scope.$root.periodSelectedKey
                scopeId: scope.$root.scopeSelectedId
                verification:
                    status: 'approved'

            if valid
                downloadService.postJson '/awac/verification/verify', data, (result) ->
                    if result.success
                        scope.$parent.verifyQuestionSet(scope.getTitleCode(), result.data)
                        scope.$emit 'TEST_CLOSING_VALIDATION'
            else
                if scope.$parent.getQuestionSetVerification(scope.getTitleCode())?
                    comment = scope.$parent.getQuestionSetVerification(scope.getTitleCode()).comment
                data =
                    questionSetCode: scope.getTitleCode()
                    refreshVerificationStatus: scope.refreshVerificationStatus
                    comment: comment
                modalService.show modalService.VERIFICATION_REJECT, data

        scope.refreshVerificationStatus = (status) ->
            scope.$parent.verifyQuestionSet(scope.getTitleCode(), status)

        scope.getVerificationClass = (valid, disabled = false) ->
            if disabled == true
                if scope.$parent.getQuestionSetVerification(scope.getTitleCode())? && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status == 'approved'
                    return 'verified_valid'
                else if scope.$parent.getQuestionSetVerification(scope.getTitleCode())? && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status == 'rejected'
                    return 'verified_rejected'
                else
                    return 'verified_unverified'
            else
                if valid
                    if scope.$parent.getQuestionSetVerification(scope.getTitleCode())? && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status == 'approved'
                        return 'verification_approved'
                    else
                        return 'verification_approval'
                else
                    if scope.$parent.getQuestionSetVerification(scope.getTitleCode())? && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status == 'rejected'
                        return 'verification_rejected'
                    else
                        return 'verification_reject'

        scope.displayVerificationRejectedMessage = ->
            if scope.$parent.getQuestionSetVerification(scope.getTitleCode())? && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status != 'approved'
                data =
                    readOnly: true
                    comment: scope.$parent.getQuestionSetVerification(scope.getTitleCode()).comment

                modalService.show modalService.VERIFICATION_REJECT, data


        scope.getVerifier = ->
            if scope.$parent.getQuestionSetVerification(scope.getTitleCode())?
                return scope.$parent.getQuestionSetVerification(scope.getTitleCode()).verifier
            return null

        scope.getVerifierName = ->
            if scope.$parent.getQuestionSetVerification(scope.getTitleCode())?
                return scope.$parent.getQuestionSetVerification(scope.getTitleCode()).verifier.firstName + " " + scope.$parent.getQuestionSetVerification(scope.getTitleCode()).verifier.lastName


        scope.isVerificationDisabled = ->
            if  $location.path() == '/submit'
                return true
            return false







