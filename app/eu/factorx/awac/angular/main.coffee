angular.module 'app.directives', ['ngAnimate', 'ngSanitize', 'ui.bootstrap', 'dangle']

angular.module 'app.filters', []

angular.module 'app.services', []

angular.module 'app.controllers', ['app.services', 'ngRoute']

angular.module 'app', [
    'app.directives',
    'app.filters',
    'app.services',
    'app.controllers'
]
