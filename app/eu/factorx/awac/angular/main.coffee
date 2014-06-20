angular.module 'app.directives', ['ngAnimate', 'ngSanitize', 'ui.select']

angular.module 'app.filters', []

angular.module 'app.services', []

angular.module 'app.controllers', ['app.services']

angular.module 'app', [
    'app.directives',
    'app.filters',
    'app.services',
    'app.controllers'
]
