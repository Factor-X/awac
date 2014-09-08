angular
.module('app.directives')
.directive "mmAwacAdminBadImporter", (directiveService, $timeout, ngTableParams, $http,$filter,downloadService,messageFlash,modalService) ->
	restrict: "E"
	scope: {}
	templateUrl: "$/angular/templates/mm-awac-admin-bad-importer.html"
	replace: true
	transclude: true
	link: (scope)->

		scope.total_bad= 0
		scope.total_info= 0
		scope.total_warning= 0
		scope.total_error= 0

		url = "awac/admin/badImporter"

		scope.import = ->

			scope.total_bad= 0
			scope.total_info= 0
			scope.total_warning= 0
			scope.total_error= 0

			modalService.show(modalService.LOADING)

			downloadService.getJson url , (result) ->
				if not result.success
					# TODO ERROR HANDLING
					messageFlash.displayError result.data.message
					modalService.close(modalService.LOADING)
				else
					for logLine in result.data.logLines

						scope.total_bad++

						logLine.messagesWarningNb = 0
						logLine.messagesInfoNb = 0
						logLine.messagesErrorNb = 0

						if logLine.messages['WARNING']?
							logLine.messagesWarningNb = logLine.messages['WARNING'].length
							scope.total_warning++

						if logLine.messages['INFO']?
							logLine.messagesInfoNb = logLine.messages['INFO'].length
							scope.total_info++

						if logLine.messages['ERROR']?
							logLine.messagesErrorNb = logLine.messages['ERROR'].length
							scope.total_error++

					scope.tableParams = new ngTableParams(
						page: 1 # show first page
						count: 100 # count per page
						sorting:
							code: "asc" # initial sorting
					,
						total: 0 # length of data
						getData: ($defer, params) ->


							orderedData = $filter("orderBy")(result.data.logLines, params.orderBy())
							$defer.resolve orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count())
							params.total result.data.logLines.length
					)
					modalService.close(modalService.LOADING)

		return