var initializeEventRoutes;
initializeEventRoutes = function(defaultResolve) {
  angular.module('app').run(function($rootScope, $location) {
    $rootScope.onFormPath = function(period, scope) {
      return $location.path($rootScope.getFormPath() + '/' + period + '/' + scope);
    };
    $rootScope.getFormPath = function() {
      return null;
    };
    return $rootScope.getDefaultRoute = function() {
      return $rootScope.getFormPath();
    };
  });
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/form/:form/:period/:scope', {
      templateUrl: function($routeParams) {
        return '$/angular/views/event/' + $routeParams.form + '.html';
      },
      controller: 'FormCtrl',
      resolve: angular.extend({
        helpPage: function() {
          return 'help_form';
        }
      }, formResolve)
    }).when('/results/:period/:scope', {
      templateUrl: '$/angular/views/results.html',
      controller: 'ResultsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help_results';
        }
      }, resultResolve)
    }).when('/actions/:period/:scope', {
      templateUrl: '$/angular/views/actions.html',
      controller: 'ActionsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help_actions';
        }
      }, defaultResolve)
    });
    return;
  });
};var initializeVerificationRoutes;
initializeVerificationRoutes = function(defaultResolve) {
  angular.module('app').run(function($rootScope, $location) {
    $rootScope.onFormPath = function(period, scope) {
      return $location.path($rootScope.getFormPath() + '/' + period + '/' + scope);
    };
    return $rootScope.getDefaultRoute = function() {
      return '/verification';
    };
  });
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/manage', {
      templateUrl: '$/angular/views/verification/manage.html',
      controller: 'VerificationManageCtrl',
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help_manage';
        }
      }, defaultResolve)
    }).when('/verification', {
      templateUrl: '$/angular/views/verification/verification.html',
      controller: 'VerificationVerificationCtrl',
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help_verification';
        }
      }, defaultResolve)
    }).when('/submit', {
      templateUrl: '$/angular/views/verification/submit.html',
      controller: 'VerificationSubmitCtrl',
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help_submit';
        }
      }, defaultResolve)
    }).when('/verification_registration/:key', {
      templateUrl: '$/angular/views/verification_registration.html',
      controller: 'VerificationRegistrationCtrl',
      anonymousAllowed: true
    }).when('/archive', {
      templateUrl: '$/angular/views/verification/archive.html',
      controller: 'VerificationArchiveCtrl',
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help_archive';
        }
      }, defaultResolve)
    }).otherwise({
      redirectTo: '/login'
    });
    return;
  });
};var initializeHouseholdRoutes;
initializeHouseholdRoutes = function(defaultResolve) {
  angular.module('app').run(function($rootScope, $location) {
    $rootScope.onFormPath = function(period, scope) {
      return $location.path($rootScope.getFormPath() + '/' + period + '/' + scope);
    };
    $rootScope.getFormPath = function() {
      return null;
    };
    return $rootScope.getDefaultRoute = function() {
      return $rootScope.getFormPath();
    };
  });
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/form/:form/:period/:scope', {
      templateUrl: function($routeParams) {
        return '$/angular/views/household/' + $routeParams.form + '.html';
      },
      controller: 'FormCtrl',
      resolve: angular.extend({
        helpPage: function() {
          return 'help_form';
        }
      }, formResolve)
    }).when('/results/:period/:scope', {
      templateUrl: '$/angular/views/results.html',
      controller: 'ResultsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help_results';
        }
      }, resultResolve)
    }).when('/actions/:period/:scope', {
      templateUrl: '$/angular/views/actions.html',
      controller: 'ActionsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help_actions';
        }
      }, defaultResolve)
    });
    return;
  });
};var initializeAdminRoutes;
initializeAdminRoutes = function(defaultResolve) {
  angular.module('app').run(function($rootScope, $location) {
    $rootScope.onFormPath = function(period, scope) {
      return $location.path($rootScope.getFormPath() + '/' + period + '/' + scope);
    };
    return $rootScope.getDefaultRoute = function() {
      return '/driver';
    };
  });
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/driver', {
      templateUrl: '$/angular/views/admin/driver.html',
      controller: 'AdminDriverManageCtrl',
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        }
      }, defaultResolve)
    }).when('/advice', {
      templateUrl: '$/angular/views/admin/manage_advices.html',
      controller: 'AdminActionAdviceCtrl',
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        }
      }, defaultResolve)
    }).otherwise({
      redirectTo: '/login'
    });
    return;
  });
};var initializeMunicipalityRoutes;
initializeMunicipalityRoutes = function(defaultResolve) {
  angular.module('app').run(function($rootScope, $location) {
    $rootScope.onFormPath = function(period, scope) {
      return $location.path($rootScope.getFormPath() + '/' + period + '/' + scope);
    };
    $rootScope.getFormPath = function() {
      return '/form/TAB_C1';
    };
    return $rootScope.getDefaultRoute = function() {
      return $rootScope.getFormPath();
    };
  });
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/form/:form/:period/:scope', {
      templateUrl: function($routeParams) {
        return '$/angular/views/municipality/' + $routeParams.form + '.html';
      },
      controller: 'FormCtrl',
      resolve: angular.extend({
        formIdentifier: function($route) {
          return $route.current.params.form;
        },
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help_form';
        }
      }, formResolve)
    }).when('/results/:period/:scope', {
      templateUrl: '$/angular/views/results.html',
      controller: 'ResultsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help_results';
        }
      }, defaultResolve)
    }).when('/actions/:period/:scope', {
      templateUrl: '$/angular/views/actions.html',
      controller: 'ActionsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help_actions';
        }
      }, defaultResolve)
    });
    return;
  });
};var initializeEnterpriseRoutes;
initializeEnterpriseRoutes = function(defaultResolve) {
  angular.module('app').run(function($rootScope, $location) {
    $rootScope.onFormPath = function(period, scope) {
      return $location.path($rootScope.getFormPath() + '/' + period + '/' + scope);
    };
    $rootScope.getFormPath = function() {
      return '/form/TAB2';
    };
    return $rootScope.getDefaultRoute = function() {
      return $rootScope.getFormPath();
    };
  });
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/form/:form/:period/:scope', {
      templateUrl: function($routeParams) {
        return '$/angular/views/enterprise/' + $routeParams.form + '.html';
      },
      controller: 'FormCtrl',
      resolve: angular.extend({
        helpPage: function() {
          return 'help_form';
        }
      }, formResolve)
    }).when('/results/:period/:scope', {
      templateUrl: '$/angular/views/results.html',
      controller: 'ResultsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help_results';
        }
      }, resultResolve)
    }).when('/actions/:period/:scope', {
      templateUrl: '$/angular/views/actions.html',
      controller: 'ActionsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help_actions';
        }
      }, defaultResolve)
    });
    return;
  });
};var initializeCommonRoutes;
initializeCommonRoutes = function(defaultResolve) {
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/login', {
      templateUrl: '$/angular/views/login.html',
      controller: 'LoginCtrl',
      anonymousAllowed: true,
      resolve: {
        testConnection: function($http, $rootScope, $location, downloadService) {
          if ($rootScope.currentPerson != null) {
            return $rootScope.toDefaultForm();
          } else {
            console.log("testAuthentication to initializeCommonRoutes ");
            return downloadService.postJson('/awac/testAuthentication', {
              interfaceName: $rootScope.instanceName
            }, function(result) {
              if (result.success) {
                $rootScope.loginSuccess(result.data, !$rootScope.isLogin());
                $rootScope.toDefaultForm();
                return true;
              } else {
                return true;
              }
            });
          }
        }
      }
    }).when('/admin', {
      templateUrl: '$/angular/views/admin.html',
      controller: 'AdminCtrl',
      resolve: defaultResolve
    }).when('/user_data', {
      templateUrl: '$/angular/views/user_data.html',
      controller: 'UserDataCtrl',
      resolve: angular.extend({
        helpPage: function() {
          return 'help_user_data';
        }
      }, defaultResolve)
    }).when('/organization_manager', {
      templateUrl: '$/angular/views/organization_manager.html',
      controller: 'OrganizationManagerCtrl',
      resolve: angular.extend({
        helpPage: function() {
          return 'help_organization_manager';
        }
      }, defaultResolve)
    }).when('/user_manager', {
      templateUrl: '$/angular/views/user_manager.html',
      controller: 'UserManagerCtrl',
      resolve: angular.extend({
        helpPage: function() {
          return 'help_user_manager';
        }
      }, defaultResolve)
    }).when('/site_manager', {
      templateUrl: '$/angular/views/site_manager.html',
      controller: 'SiteManagerCtrl',
      resolve: angular.extend({
        helpPage: function() {
          return 'help_site_manager';
        }
      }, defaultResolve)
    }).when('/registration/:key', {
      templateUrl: '$/angular/views/user_registration.html',
      controller: 'RegistrationCtrl',
      anonymousAllowed: true
    }).when('/noScope', {
      templateUrl: '$/angular/views/no_scope.html',
      controller: 'NoScopeCtrl',
      anonymousAllowed: true,
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        }
      }, defaultResolve)
    }).otherwise({
      redirectTo: '/login'
    });
    return;
  });
};var initializeLittleEmitterRoutes;
initializeLittleEmitterRoutes = function(defaultResolve) {
  angular.module('app').run(function($rootScope, $location) {
    $rootScope.onFormPath = function(period, scope) {
      return $location.path($rootScope.getFormPath() + '/' + period + '/' + scope);
    };
    $rootScope.getFormPath = function() {
      return null;
    };
    return $rootScope.getDefaultRoute = function() {
      return $rootScope.getFormPath();
    };
  });
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/form/:form/:period/:scope', {
      templateUrl: function($routeParams) {
        return '$/angular/views/littleEmitter/' + $routeParams.form + '.html';
      },
      controller: 'FormCtrl',
      resolve: angular.extend({
        helpPage: function() {
          return 'help_form';
        }
      }, formResolve)
    }).when('/results/:period/:scope', {
      templateUrl: '$/angular/views/results.html',
      controller: 'ResultsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help_results';
        }
      }, resultResolve)
    }).when('/actions/:period/:scope', {
      templateUrl: '$/angular/views/actions.html',
      controller: 'ActionsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help_actions';
        }
      }, defaultResolve)
    });
    return;
  });
};var defaultResolve, formResolve, iName, resultResolve;
angular.module('app.directives', ['ngAnimate', 'ngSanitize', 'ui.bootstrap', 'ui.bootstrap.datetimepicker']);
angular.module('app.filters', []);
angular.module('app.services', []);
angular.module('app.controllers', ['app.services', 'ngRoute', 'angularFileUpload', 'tmh.dynamicLocale', 'ngTable']);
angular.module('app', ['app.directives', 'app.filters', 'app.services', 'app.controllers']);
angular.module("tmh.dynamicLocale").config(function(tmhDynamicLocaleProvider) {
  return tmhDynamicLocaleProvider.localeLocationPattern('assets/components/angular-i18n/angular-locale_{{locale}}.js');
});
defaultResolve = {
  testConnection: function($http, $rootScope, $location, downloadService) {
    if (!$rootScope.currentPerson) {
      console.log('test authentication because not curennt person : defaultResolve');
      return downloadService.postJson('/awac/testAuthentication', {
        interfaceName: $rootScope.instanceName
      }, function(result) {
        if (result.success) {
          return $rootScope.loginSuccess(result.data, !$rootScope.isLogin());
        } else {
          console.log('to login  : defaultResolve');
          return $location.path('/login');
        }
      });
    }
  }
};
formResolve = {
  testConnection: function($http, $rootScope, $location, downloadService, $route) {
    if (!$rootScope.currentPerson) {
      console.log('test authentication because not curennt person : formResolve');
      return downloadService.postJson('/awac/testAuthentication', {
        interfaceName: $rootScope.instanceName
      }, function(result) {
        if (result.success) {
          $rootScope.loginSuccess(result.data, !$rootScope.isLogin());
          if ($rootScope.testForm($route.current.params.period, $route.current.params.scope) === false) {
            return $location.path("/noScope");
          }
        } else {
          console.log('to login  : formResolve');
          return $location.path('/login');
        }
      });
    } else {
      if ($rootScope.testForm($route.current.params.period, $route.current.params.scope) === false) {
        return $location.path('/noScope');
      }
    }
  }
};
resultResolve = {
  testConnection: function($http, $rootScope, $location, downloadService, $route) {
    if (!$rootScope.currentPerson) {
      console.log('test authentication because not curennt person defaultResolve : resultResolve');
      return downloadService.postJson('/awac/testAuthentication', {
        interfaceName: $rootScope.instanceName
      }, function(result) {
        if (result.success) {
          $rootScope.loginSuccess(result.data, !$rootScope.isLogin());
          if ($rootScope.testForm($route.current.params.period, $route.current.params.scope) === false) {
            return $location.path('/noScope');
          }
        } else {
          console.log('to login  : resultResolve');
          return $location.path('/login');
        }
      });
    }
  }
};
if (document.querySelector("meta[name=app]") != null) {
  iName = document.querySelector("meta[name=app]").getAttribute("content");
  if (iName === "municipality") {
    initializeMunicipalityRoutes(defaultResolve);
  } else if (iName === "enterprise") {
    initializeEnterpriseRoutes(defaultResolve);
  } else if (iName === "verification") {
    initializeVerificationRoutes(defaultResolve);
  } else if (iName === "littleEmitter") {
    initializeLittleEmitterRoutes(defaultResolve);
  } else if (iName === "event") {
    initializeEventRoutes(defaultResolve);
  } else if (iName === "household") {
    initializeHouseholdRoutes(defaultResolve);
  } else if (iName === "admin") {
    initializeAdminRoutes(defaultResolve);
  }
}
initializeCommonRoutes(defaultResolve);
angular.module('app').run(function($rootScope) {
  return $rootScope.instanceName = iName;
});
angular.module('app').config(function($httpProvider) {
  return $httpProvider.interceptors.push(function() {
    return {
      'request': function(config) {
        return config;
      },
      'response': function(response) {
        if (response.data.__type === "eu.factorx.awac.dto.awac.get.DownloadFileDTO") {
          setTimeout(function() {
            var blob, byteArray, byteCharacters, byteNumbers, filename, i, _ref;
            byteCharacters = atob(response.data.base64);
            byteNumbers = new Array(byteCharacters.length);
            for (i = 0, _ref = byteCharacters.length; (0 <= _ref ? i < _ref : i > _ref); (0 <= _ref ? i += 1 : i -= 1)) {
              byteNumbers[i] = byteCharacters.charCodeAt(i);
            }
            byteArray = new Uint8Array(byteNumbers);
            blob = new Blob([byteArray], {
              type: response.data.mimeType
            });
            filename = response.data.filename;
            return saveAs(blob, filename);
          }, 0);
        }
        return response;
      }
    };
  });
});angular.module('app.services').service("modalService", function($rootScope) {
  this.LOADING = 'loading';
  this.DOCUMENT_MANAGER = 'DOCUMENT_MANAGER';
  this.CONFIRMATION_EXIT_FORM = 'confirmation-exit-form';
  this.QUESTION_COMMENT = 'question-comment';
  this.EMAIL_CHANGE = 'email-change';
  this.PASSWORD_CHANGE = 'password-change';
  this.CONNECTION_PASSWORD_CHANGE = 'connection-password-change';
  this.INVITE_USER = 'invite-user';
  this.EDIT_SITE = 'edit-site';
  this.ADD_USER_SITE = 'add-user-site';
  this.EDIT_EVENT = 'edit-event';
  this.CONFIRM_CLOSING = 'confirm-closing';
  this.HELP = 'help';
  this.CREATE_REDUCTION_ACTION = 'create-reduction-action';
  this.EDIT_OR_CREATE_REDUCTION_ACTION = 'edit-or-create-reduction-action';
  this.REQUEST_VERIFICATION = 'request_verification';
  this.VERIFICATION_ASSIGN = 'verification_assign';
  this.VERIFICATION_REJECT = 'verification_reject';
  this.VERIFICATION_REJECT = 'verification_reject';
  this.CONSULT_EVENT = 'consult-event';
  this.VERIFICATION_FINALIZATION = 'verification-finalization';
  this.PASSWORD_CONFIRMATION = 'password-confirmation';
  this.VERIFICATION_CONFIRMATION = 'verification-confirmation';
  this.VERIFICATION_DOCUMENT = 'verification-document';
  this.VERIFICATION_FINALIZATION_VISUALIZATION = 'verification-finalization-visualization';
  this.DRIVER_ADD_VALUE = 'driver-add-value';
  this.CONFIRM_DIALOG = 'confirm-dialog';
  this.show = function(modalName, params) {
    var args;
    args = [];
    args.show = true;
    args.params = params;
    args.target = modalName;
    $rootScope.displayModalBackground = true;
    return $rootScope.$broadcast('SHOW_MODAL', args);
  };
  this.close = function(modalName) {
    var args;
    args = [];
    args.show = false;
    args.target = modalName;
    $rootScope.displayModalBackground = false;
    return $rootScope.$broadcast('SHOW_MODAL', args);
  };
  return;
});angular.module('app.services').service("downloadService", function($http, $q, messageFlash, translationService) {
  this.downloadsInProgress = 0;
  this.getDownloadsInProgress = function() {
    return this.downloadsInProgress;
  };
  this.getJson = function(url, callback) {
    var deferred, promise;
    console.log("GET URL TO " + url);
    deferred = $q.defer();
    this.downloadsInProgress++;
    promise = $http({
      method: "GET",
      url: url,
      headers: {
        "Content-Type": "application/json"
      }
    });
    promise.success(function(data, status, headers, config) {
      this.downloadsInProgress--;
      return deferred.resolve(callback({
        data: data,
        status: status,
        headers: headers,
        config: config,
        success: true
      }));
    });
    promise.error(function(data, status, headers, config) {
      this.downloadsInProgress--;
      if (!!data) {
        messageFlash.displayError(translationService.translateExceptionsDTO(data));
      }
      return deferred.resolve(callback({
        data: data,
        status: status,
        headers: headers,
        config: config,
        success: false
      }));
    });
    return deferred.promise;
  };
  this.postJson = function(url, data, callback, options) {
    var deferred, promise;
    console.log("POST URL TO " + url);
    deferred = $q.defer();
    if (data === null) {
      data = {};
    }
    this.downloadsInProgress++;
    promise = $http({
      method: "POST",
      url: url,
      data: data,
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json,application/octet-stream"
      }
    });
    promise.success(function(data, status, headers, config) {
      this.downloadsInProgress--;
      return deferred.resolve(callback({
        data: data,
        status: status,
        headers: headers,
        config: config,
        success: true
      }));
    });
    promise.error(function(data, status, headers, config) {
      this.downloadsInProgress--;
      if (!!data) {
        messageFlash.displayError(translationService.translateExceptionsDTO(data));
      }
      return deferred.resolve(callback({
        data: data,
        status: status,
        headers: headers,
        config: config,
        success: false
      }));
    });
    return deferred.promise;
  };
  return;
});angular.module('app.services').service("messageFlash", function(translateTextFilter) {
  this.display = function(type, message, opts) {
    var options;
    options = {
      message: translateTextFilter(message),
      type: type,
      hideAfter: 5,
      showCloseButton: true
    };
    return Messenger().post(angular.extend(options, angular.copy(opts)));
  };
  this.displaySuccess = function(message, opts) {
    return this.display('success', message, opts);
  };
  this.displayInfo = function(message, opts) {
    return this.display('info', message, opts);
  };
  this.displayError = function(message, opts) {
    return this.display('error', message, opts);
  };
  return;
});angular.module('app.services').service("generateId", function($rootScope) {
  this.generate = function() {
    var i, possible, text;
    text = "";
    possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    i = 0;
    while (i < 20) {
      text += possible.charAt(Math.floor(Math.random() * possible.length));
      i++;
    }
    return text;
  };
  return;
});angular.module('app.services').service('loggerService', function() {
  var svc;
  svc = this;
  svc.initialize = function() {
    var layout, log;
    log = log4javascript.getLogger('main');
    svc.appender = new log4javascript.InPageAppender('logger', true, false, true, '100%', '100%');
    layout = new log4javascript.PatternLayout("%d [%-5p] %15c - %m");
    svc.appender.setLayout(layout);
    log.addAppender(svc.appender);
    return log.info("Logger initialized");
  };
  svc.get = function(name) {
    var log;
    log = log4javascript.getLogger(name);
    log.addAppender(svc.appender);
    return log;
  };
  return;
});angular.module('app.services').service("translationService", function($rootScope, $filter, $http) {
  var svc;
  svc = this;
  svc.elements = null;
  svc.initialize = function(lang) {
    $http({
      method: "GET",
      url: "/awac/translations/" + lang,
      headers: {
        "Content-Type": "application/json"
      }
    }).success(function(data, status, headers, config) {
      svc.elements = data.lines;
      return $rootScope.$broadcast("LOAD_FINISHED", {
        type: "TRANSLATIONS",
        success: true
      });
    }).error(function(data, status, headers, config) {
      svc.elements = [];
      return $rootScope.$broadcast("LOAD_FINISHED", {
        type: "TRANSLATIONS",
        success: false
      });
    });
    return;
  };
  svc.get = function(code, count) {
    var txt, v;
    if (svc.elements == null) {
      return "";
    }
    v = svc.elements[code];
    if (!(v != null)) {
      return null;
    }
    if (count != null) {
      if ("" + count === "0") {
        txt = v.zero || v.fallback;
      } else if ("" + count === "1") {
        txt = v.one || v.fallback;
      } else {
        txt = v.more || v.fallback;
      }
      txt = txt.replace(/\{0\}/g, count);
    } else {
      txt = v.fallback || '';
    }
    return txt;
  };
  svc.translateExceptionsDTO = function(exception) {
    if ((exception.params != null) && Object.keys(exception.params).length > 0) {
      return $filter('translateTextWithVars')(exception.messageToTranslate, exception.params);
    } else if (exception.messageToTranslate != null) {
      return $filter('translate')(exception.messageToTranslate);
    } else {
      return exception.message;
    }
  };
  return;
});angular.module('app.services').service("directiveService", function($sce) {
  this.autoScope = function(s) {
    var k, res, v;
    res = {};
    for (k in s) {
      v = s[k];
      res[k] = v;
      if (k.slice(0, 2) === 'ng' && v === '=') {
        res[k[2].toLowerCase() + k.slice(3)] = '@';
      }
    }
    return res;
  };
  this.autoScopeImpl = function(s, name) {
    var fget, key, val;
    s.$$NAME = name;
    for (key in s) {
      val = s[key];
      if (key.slice(0, 2) === 'ng') {
        fget = function(scope, k) {
          return function() {
            var v;
            v = 0;
            if (scope[k] === void 0 || scope[k] === null || scope[k] === '') {
              v = scope[k[2].toLowerCase() + k.slice(3)];
            } else {
              v = scope[k];
            }
            if (scope['decorate' + k.slice(2)]) {
              return scope['decorate' + k.slice(2)](v);
            } else {
              return v;
            }
          };
        };
        s['get' + key.slice(2)] = fget(s, key);
      }
    }
    s.isTrue = function(v) {
      return v === true || v === 'true' || v === 'y';
    };
    s.isFalse = function(v) {
      return v === false || v === 'false' || v === 'n';
    };
    s.isNull = function(v) {
      return v === null;
    };
    return s.html = function(v) {
      return $sce.trustAsHtml(v);
    };
  };
  return;
});angular.module('app.filters').filter("stringToFloat", function($sce, translationService) {
  return function(input) {
    if (input != null) {
      return parseFloat(input);
    }
  };
});angular.module('app.filters').filter("translateText", function($sce, translationService) {
  return function(input, count) {
    var text;
    text = translationService.get(input, count);
    if (text != null) {
      return text;
    }
    return input;
  };
});angular.module('app.filters').filter("trustAsHtml", function($sce) {
  return function(input) {
    return $sce.trustAsHtml(input);
  };
});angular.module('app.filters').filter("translateTextWithVars", function($sce, translationService) {
  return function(input, vars) {
    var k, text, v;
    text = translationService.get(input, null);
    if (text != null) {
      for (k in vars) {
        v = vars[k];
        text = text.replace('{' + k + '}', v);
      }
      return text;
    }
    return input;
  };
});angular.module('app.filters').filter("nullToZero", function($sce, translationService) {
  return function(input) {
    if (input === void 0 || input === null) {
      return 0;
    } else {
      return parseFloat(input);
    }
  };
});angular.module('app.filters').filter("numberToI18NOrLess", function($filter, translationService) {
  return function(input) {
    var original, rounded;
    if (input != null) {
      original = parseFloat(input);
      rounded = Math.round(original * 1000.0) / 1000.0;
      if (rounded < 0.001) {
        if (original > 0) {
          return translationService.get('LESS_THAN_MINIMUM');
        } else {
          return '';
        }
      } else {
        return $filter("number")(rounded, 3);
      }
    }
    return "";
  };
});angular.module('app.filters').filter("numberToI18N", function($filter) {
  return function(input) {
    var original, rounded;
    if (input != null) {
      original = parseFloat(input);
      rounded = Math.round(original * 100.0) / 100.0;
      return $filter("number")(rounded, 2);
    }
    return "";
  };
});angular.module('app.filters').filter("translate", function($sce, translationService) {
  return function(input, count) {
    var text;
    text = translationService.get(input, count);
    if (text != null) {
      return $sce.trustAsHtml("<span class=\"translated-text\" data-code=\"" + input + "\">" + text + "</span>");
    } else {
      return $sce.trustAsHtml("<span class=\"translated-text translation-missing\" data-code=\"" + input + "\">[" + input + "]</span>");
    }
  };
});angular.module('app.filters').filter("translateWithVars", function($sce, translationService) {
  return function(input, vars) {
    var k, text, v;
    text = translationService.get(input, null);
    if (text != null) {
      for (k in vars) {
        v = vars[k];
        text = text.replace('{' + k + '}', v);
      }
      return $sce.trustAsHtml("<span class=\"translated-text\" data-code=\"" + input + "\">" + text + "</span>");
    } else {
      return $sce.trustAsHtml("<span class=\"translated-text translation-missing\" data-code=\"" + input + "\">[" + input + "]</span>");
    }
  };
});angular.module('app.filters').filter("numberToI18NRoundedOrFullIfLessThanOne", function($filter) {
  return function(input) {
    var original;
    if (input != null) {
      original = parseFloat(input);
      if (original < 1) {
        if (original === 0) {
          return 0;
        } else {
          return $filter("number")(original, 12);
        }
      } else {
        return $filter("number")(original, 3);
      }
    }
    return "";
  };
});
angular.module('app.directives').directive('mmNotNullValidator', function(){
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            console.log(attrs);

            ctrl.$parsers.unshift(function(viewValue) {

                var o = {};

                for(k in attrs) {
                    if( k.substring(0, 'mmNotNullValidator'.length) == 'mmNotNullValidator' && k.length > 'mmNotNullValidator'.length) {
                        arg = k.substring('mmNotNullValidator'.length);
                        o[arg.toLowerCase()] = attrs[k];
                    }
                }

                ;

                function validate(v) {
    return v != null
}

                ;

                var result = validate(viewValue, o);

                if (result) {
                  ctrl.$setValidity('not-null', true);
                  return viewValue;
                } else {
                  ctrl.$setValidity('not-null', false);
                  return undefined;
                }

            });
        }
    };
});
            
angular.module('app.directives').directive('mmPatternValidator', function(){
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            console.log(attrs);

            ctrl.$parsers.unshift(function(viewValue) {

                var o = {};

                for(k in attrs) {
                    if( k.substring(0, 'mmPatternValidator'.length) == 'mmPatternValidator' && k.length > 'mmPatternValidator'.length) {
                        arg = k.substring('mmPatternValidator'.length);
                        o[arg.toLowerCase()] = attrs[k];
                    }
                }

                ;

                function validate(value, args) {
    var re = new RegExp(eval(args.regexp));
    return re.test(value);
}

                ;

                var result = validate(viewValue, o);

                if (result) {
                  ctrl.$setValidity('pattern', true);
                  return viewValue;
                } else {
                  ctrl.$setValidity('pattern', false);
                  return undefined;
                }

            });
        }
    };
});
            
angular.module('app.directives').directive('mmNullValidator', function(){
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            console.log(attrs);

            ctrl.$parsers.unshift(function(viewValue) {

                var o = {};

                for(k in attrs) {
                    if( k.substring(0, 'mmNullValidator'.length) == 'mmNullValidator' && k.length > 'mmNullValidator'.length) {
                        arg = k.substring('mmNullValidator'.length);
                        o[arg.toLowerCase()] = attrs[k];
                    }
                }

                ;

                function validate(v) {
    return v == null
}

                ;

                var result = validate(viewValue, o);

                if (result) {
                  ctrl.$setValidity('null', true);
                  return viewValue;
                } else {
                  ctrl.$setValidity('null', false);
                  return undefined;
                }

            });
        }
    };
});
            
angular.module('app.directives').directive('mmRangeValidator', function(){
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            console.log(attrs);

            ctrl.$parsers.unshift(function(viewValue) {

                var o = {};

                for(k in attrs) {
                    if( k.substring(0, 'mmRangeValidator'.length) == 'mmRangeValidator' && k.length > 'mmRangeValidator'.length) {
                        arg = k.substring('mmRangeValidator'.length);
                        o[arg.toLowerCase()] = attrs[k];
                    }
                }

                ;

                function validate(value, args) {
    if(parseFloat(value)==null || parseFloat(value)==NaN)
        return false;
    return parseFloat(value)>=parseInt(args.min) && parseFloat(value)<=parseInt(args.max);
}

                ;

                var result = validate(viewValue, o);

                if (result) {
                  ctrl.$setValidity('range', true);
                  return viewValue;
                } else {
                  ctrl.$setValidity('range', false);
                  return undefined;
                }

            });
        }
    };
});
            
angular.module('app.directives').directive('mmSizeValidator', function(){
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            console.log(attrs);

            ctrl.$parsers.unshift(function(viewValue) {

                var o = {};

                for(k in attrs) {
                    if( k.substring(0, 'mmSizeValidator'.length) == 'mmSizeValidator' && k.length > 'mmSizeValidator'.length) {
                        arg = k.substring('mmSizeValidator'.length);
                        o[arg.toLowerCase()] = attrs[k];
                    }
                }

                ;

                function validate(value, args) {
    if(value == null)
        if(args.min > 0)
            return false;
        return true;
    var re = value.length >= parseInt(args.min) && value.length <= parseInt(args.max);
    return re;
}

                ;

                var result = validate(viewValue, o);

                if (result) {
                  ctrl.$setValidity('size', true);
                  return viewValue;
                } else {
                  ctrl.$setValidity('size', false);
                  return undefined;
                }

            });
        }
    };
});
            angular.module('app.directives').directive("mmAwacAdminBadImporter", function(directiveService, $timeout, ngTableParams, $http, $filter, downloadService, messageFlash, modalService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-admin-bad-importer.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      var url;
      scope.total_bad = 0;
      scope.total_info = 0;
      scope.total_warning = 0;
      scope.total_error = 0;
      url = "awac/admin/badImporter/" + scope.$root.instanceName;
      scope["import"] = function() {
        scope.total_bad = 0;
        scope.total_bad_info = 0;
        scope.total_bad_warning = 0;
        scope.total_bad_error = 0;
        scope.total_question = 0;
        scope.total_question_info = 0;
        scope.total_question_warning = 0;
        scope.total_question_error = 0;
        modalService.show(modalService.LOADING);
        return downloadService.getJson(url, function(result) {
          var logBad, logLine, logQuestion, _i, _len, _ref;
          if (!result.success) {
            return modalService.close(modalService.LOADING);
          } else {
            logBad = [];
            logQuestion = [];
            _ref = result.data.logLines;
            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              logLine = _ref[_i];
              logLine.messagesWarningNb = 0;
              logLine.messagesInfoNb = 0;
              logLine.messagesErrorNb = 0;
              if (logLine.logCategory === 'BAD') {
                scope.total_bad++;
                logBad.push(logLine);
                if (logLine.messages['WARNING'] != null) {
                  logLine.messagesWarningNb = logLine.messages['WARNING'].length;
                  scope.total_bad_warning++;
                }
                if (logLine.messages['INFO'] != null) {
                  logLine.messagesInfoNb = logLine.messages['INFO'].length;
                  scope.total_bad_info++;
                }
                if (logLine.messages['ERROR'] != null) {
                  logLine.messagesErrorNb = logLine.messages['ERROR'].length;
                  scope.total_bad_error++;
                }
              }
              if (logLine.logCategory === 'QUESTION') {
                scope.total_question++;
                logQuestion.push(logLine);
                if (logLine.messages['WARNING'] != null) {
                  logLine.messagesWarningNb = logLine.messages['WARNING'].length;
                  scope.total_question_warning++;
                }
                if (logLine.messages['INFO'] != null) {
                  logLine.messagesInfoNb = logLine.messages['INFO'].length;
                  scope.total_question_info++;
                }
                if (logLine.messages['ERROR'] != null) {
                  logLine.messagesErrorNb = logLine.messages['ERROR'].length;
                  scope.total_question_error++;
                }
              }
            }
            scope.tableParams = new ngTableParams({
              page: 1,
              count: 100,
              sorting: {
                code: "asc"
              }
            }, {
              total: 0,
              getData: function($defer, params) {
                var orderedData;
                orderedData = $filter("orderBy")(logBad, params.orderBy());
                $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                return params.total(logBad.length);
              }
            });
            scope.tableParams2 = new ngTableParams({
              page: 1,
              count: 100,
              sorting: {
                code: "asc"
              }
            }, {
              total: 0,
              getData: function($defer, params) {
                var orderedData;
                orderedData = $filter("orderBy")(logQuestion, params.orderBy());
                $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                return params.total(logQuestion.length);
              }
            });
            return modalService.close(modalService.LOADING);
          }
        });
      };
      return;
    }
  };
});angular.module('app.directives').directive("mmNotImplemented", function(directiveService) {
  return {
    restrict: "A",
    scope: {},
    link: function(scope, elem, attrs) {
      elem.css('opacity', '0.25');
      return elem.bind('click', function(e) {
        e.preventDefault();
        e.stopPropagation();
        return false;
      });
    }
  };
});angular.module('app.directives').directive("mmAwacModalInviteUser", function(directiveService, downloadService, translationService, messageFlash, $rootScope) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-invite-user.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.inviteEmailInfo = {
        field: "",
        inputName: 'email',
        fieldTitle: "USER_INVITATION_EMAIL_FIELD_TITLE",
        placeholder: "USER_INVITATION_EMAIL_FIELD_PLACEHOLDER",
        validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
        validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT",
        focus: function() {
          return true;
        }
      };
      $scope.allFieldValid = function() {
        if ($scope.inviteEmailInfo.isValid) {
          return true;
        }
        return false;
      };
      $scope.save = function() {
        var data;
        if (!$scope.allFieldValid()) {
          return false;
        }
        $scope.isLoading = true;
        data = {
          invitationEmail: $scope.inviteEmailInfo.field,
          organizationName: $rootScope.organizationName
        };
        downloadService.postJson('/awac/invitation', data, function(result) {
          if (result.success) {
            messageFlash.displaySuccess(translationService.get("INVITATION_EMAIL_SENT"));
            return $scope.close();
          } else {
            return $scope.isLoading = false;
          }
        });
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.INVITE_USER);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalFieldText", function(directiveService, $timeout) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngInfo: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-field-text.html",
    replace: true,
    transclude: true,
    compile: function() {
      return {
        pre: function(scope) {
          return directiveService.autoScopeImpl(scope);
        },
        post: function(scope) {
          directiveService.autoScopeImpl(scope);
          scope.isValidationDefined = !!scope.getInfo().validationRegex || !!scope.getInfo().validationFct;
          scope.hideIsValidIcon = !!scope.getInfo().hideIsValidIcon;
          scope.fieldType = !!scope.getInfo().fieldType ? scope.getInfo().fieldType : "text";
          if (!scope.getInfo().field) {
            scope.getInfo().field = "";
          }
          if (scope.getInfo().isValid === void 0) {
            scope.getInfo().isValid = !scope.isValidationDefined;
          }
          if (scope.isValidationDefined) {
            scope.$watch('getInfo().field', function(n, o) {
              if (n !== o) {
                return scope.isValid();
              }
            });
          }
          scope.isValid = function() {
            var isValid;
            if (scope.getInfo().disabled === true || scope.getInfo().hidden === true) {
              scope.getInfo().isValid = true;
              return;
            }
            if (!scope.getInfo().field) {
              scope.getInfo().field = "";
            }
            isValid = true;
            if (typeof (scope.getInfo().field) !== 'string') {
              scope.getInfo().field += "";
            }
            if (scope.getInfo().validationRegex != null) {
              isValid = !!scope.getInfo().field.match(scope.getInfo().validationRegex);
            }
            if (scope.getInfo().validationFct != null) {
              isValid = isValid && scope.getInfo().validationFct();
            }
            scope.getInfo().isValid = isValid;
            return;
          };
          scope.isValid();
          scope.logField = function() {
            return console.log(scope.getInfo());
          };
          scope.errorMessage = "";
          return scope.setErrorMessage = function(errorMessage) {
            scope.errorMessage = errorMessage;
            if (scope.lastTimeOut != null) {
              $timeout.cancel(scope.lastTimeOut);
            }
            return scope.lastTimeOut = $timeout(function() {
              scope.errorMessage = "";
              return scope.lastTimeOut = null;
            }, 2000);
          };
        }
      };
    }
  };
});angular.module('app.directives').directive("mmAwacModalHelp", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-help.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      if ($scope.getParams().template != null) {
        $scope.url = "$/angular/views/" + $scope.$root.instanceName + "/" + $scope.getParams().template + "_" + $scope.$root.language + ".html";
      }
      return $scope.close = function() {
        return modalService.close(modalService.HELP);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalPasswordChange", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-password-change.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.validatePasswordConfirmField = function() {
        if ((($scope.newPasswordInfo.isValid != null) === true && true > 0) && $scope.newPasswordInfo.field === $scope.newPasswordConfirmInfo.field) {
          return true;
        }
        return false;
      };
      $scope.oldPasswordInfo = {
        inputName: 'password',
        fieldTitle: "PASSWORD_CHANGE_FORM_OLD_PASSWORD_FIELD_TITLE",
        fieldType: "password",
        placeholder: "PASSWORD_CHANGE_FORM_OLD_PASSWORD_FIELD_PLACEHOLDER",
        validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        field: "",
        focus: function() {
          return true;
        }
      };
      $scope.newPasswordInfo = {
        inputName: 'password',
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_TITLE",
        fieldType: "password",
        placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_PLACEHOLDER",
        validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        field: ""
      };
      $scope.newPasswordConfirmInfo = {
        inputName: 'password',
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE",
        fieldType: "password",
        placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_PLACEHOLDER",
        validationFct: $scope.validatePasswordConfirmField,
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        field: ""
      };
      $scope.allFieldValid = function() {
        if ($scope.oldPasswordInfo.isValid && $scope.newPasswordInfo.isValid && $scope.newPasswordConfirmInfo.isValid) {
          return true;
        }
        return false;
      };
      $scope.save = function() {
        var data;
        if (!$scope.allFieldValid()) {
          return false;
        }
        $scope.isLoading = true;
        data = {
          oldPassword: $scope.oldPasswordInfo.field,
          newPassword: $scope.newPasswordInfo.field
        };
        downloadService.postJson('/awac/user/password/save', data, function(result) {
          if (result.success) {
            messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
            return $scope.close();
          } else {
            return $scope.isLoading = false;
          }
        });
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.PASSWORD_CHANGE);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalQuestionComment", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-question-comment.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.close = function() {
        return modalService.close(modalService.QUESTION_COMMENT);
      };
      $scope.comment = $scope.ngParams.comment;
      return $scope.save = function() {
        $scope.ngParams.save($scope.comment);
        return $scope.close();
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalPasswordConfirmation", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-password-confirmation.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.fields = {
        myPassword: {
          fieldType: "password",
          inputName: 'password',
          fieldTitle: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_TITLE",
          placeholder: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_PLACEHOLDER",
          validationRegex: "^\\S{5,20}$",
          validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
          hideIsValidIcon: true,
          focus: function() {
            return true;
          }
        }
      };
      $scope.allFieldValid = function() {
        var key, _i, _len, _ref;
        _ref = Object.keys($scope.fields);
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          key = _ref[_i];
          if (key !== '$$hashKey') {
            if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
              return false;
            }
          }
        }
        return true;
      };
      $scope.save = function() {
        var data;
        if ($scope.allFieldValid()) {
          $scope.isLoading = true;
          data = $scope.getParams().data;
          data.password = $scope.fields.myPassword.field;
          downloadService.postJson($scope.getParams().url, data, function(result) {
            $scope.isLoading = false;
            if (result.success) {
              if ($scope.getParams().successMessage != null) {
                messageFlash.displaySuccess(translationService.get($scope.getParams().successMessage));
              }
              if ($scope.getParams().afterSave != null) {
                $scope.getParams().afterSave();
              }
              return $scope.close();
            }
          });
        }
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.PASSWORD_CONFIRMATION);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalEditSite", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-edit-site.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.organizationStructureList = ['ORGANIZATION_STRUCTURE_1', 'ORGANIZATION_STRUCTURE_2', 'ORGANIZATION_STRUCTURE_3', 'ORGANIZATION_STRUCTURE_4', 'ORGANIZATION_STRUCTURE_5'];
      $scope.organizationStructure = $scope.organizationStructureList[0];
      $scope.createNewSite = true;
      if ($scope.getParams().site != null) {
        $scope.site = angular.copy($scope.getParams().site);
        $scope.createNewSite = false;
        $scope.organizationStructure = $scope.site.organizationalStructure;
      } else {
        $scope.site = {};
        $scope.site.percentOwned = 100;
      }
      $scope.fields = {
        name: {
          fieldTitle: "NAME",
          field: $scope.site.name,
          validationRegex: "^.{1,255}$",
          validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH",
          focus: function() {
            return true;
          }
        },
        description: {
          fieldTitle: "DESCRIPTION",
          fieldType: 'textarea',
          validationRegex: "^.{0,65000}$",
          validationMessage: "CONTROL_FIELD_DEFAULT_TEXT",
          field: $scope.site.description,
          hideIsValidIcon: true
        },
        nace: {
          fieldTitle: "SITE_MANAGER_NACE_CODE",
          validationRegex: "^.{0,255}$",
          validationMessage: "CONTROL_FIELD_DEFAULT_STRING",
          field: $scope.site.naceCode,
          hideIsValidIcon: true
        },
        percentOwned: {
          fieldTitle: "SITE_MANAGER_PERCENT_OWNED",
          validationFct: function() {
            if ($scope.fields.percentOwned.field > 0 && $scope.fields.percentOwned.field <= 100) {
              return true;
            }
            return false;
          },
          validationMessage: "CONTROL_FIELD_DEFAULT_PERCENT_MAX_100",
          field: $scope.site.percentOwned,
          numbersOnly: 'double',
          description: 'SITE_MANAGER_PERCENT_OWNED_DESC'
        }
      };
      $scope.allFieldValid = function() {
        var key, _i, _len, _ref;
        _ref = Object.keys($scope.fields);
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          key = _ref[_i];
          if (key !== '$$hashKey') {
            if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
              return false;
            }
          }
        }
        return true;
      };
      $scope.save = function() {
        var data;
        if ($scope.allFieldValid()) {
          data = {};
          data.name = $scope.fields.name.field;
          data.description = $scope.fields.description.field;
          data.naceCode = $scope.fields.nace.field;
          data.organizationalStructure = $scope.organizationStructure;
          data.percentOwned = $scope.fields.percentOwned.field;
          $scope.isLoading = true;
          if ($scope.getParams().site != null) {
            data.id = $scope.getParams().site.id;
            downloadService.postJson('/awac/site/edit', data, function(result) {
              if (result.success) {
                messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
                $scope.getParams().site.name = $scope.fields.name.field;
                $scope.getParams().site.description = $scope.fields.description.field;
                $scope.getParams().site.naceCode = $scope.fields.nace.field;
                $scope.getParams().site.organizationalStructure = $scope.organizationStructure;
                $scope.getParams().site.percentOwned = $scope.fields.percentOwned.field;
                $scope.getParams().refreshMySites();
                return $scope.close();
              } else {
                return $scope.isLoading = false;
              }
            });
          } else {
            downloadService.postJson('/awac/site/create', data, function(result) {
              if (result.success) {
                messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
                $scope.getParams().organization.sites.push(result.data);
                $scope.$root.mySites.push(result.data);
                $scope.getParams().refreshMySites();
                return $scope.close();
              } else {
                return $scope.isLoading = false;
              }
            });
          }
        }
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.EDIT_SITE);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalCreateReductionAction", function(directiveService, downloadService, translationService, messageFlash, $upload, $window) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-create-reduction-action.html",
    controller: function($scope, modalService) {
      var action, _ref, _ref2, _ref3;
      directiveService.autoScopeImpl($scope);
      $scope.typeOptions = $scope.getParams().typeOptions;
      $scope.statusOptions = $scope.getParams().statusOptions;
      $scope.gwpUnits = $scope.getParams().gwpUnits;
      action = $scope.getParams().action;
      $scope.editMode = !!action;
      if ($scope.editMode) {
        $scope.action = angular.copy(action);
      } else {
        $scope.action = {
          typeKey: '1',
          statusKey: '1',
          scopeTypeKey: '1'
        };
      }
      $scope.getDefaultDueDate = function() {
        var defaultDueDate;
        defaultDueDate = new Date();
        defaultDueDate.setFullYear(defaultDueDate.getFullYear() + 1);
        return defaultDueDate;
      };
      $scope.title = {
        inputName: 'title',
        field: $scope.action.title,
        fieldTitle: "REDUCTION_ACTION_TITLE_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_TITLE_FIELD_PLACEHOLDER",
        validationRegex: "^.{1,255}$",
        validationMessage: "REDUCTION_ACTION_TITLE_WRONG_LENGTH",
        hideIsValidIcon: true,
        focus: function() {
          return true;
        }
      };
      $scope.typeKey = {
        inputName: 'typeKey',
        field: $scope.action.typeKey,
        fieldTitle: "REDUCTION_ACTION_TYPE_FIELD_TITLE"
      };
      $scope.statusKey = {
        inputName: 'statusKey',
        field: $scope.action.statusKey,
        fieldTitle: "REDUCTION_ACTION_STATUS_FIELD_TITLE"
      };
      $scope.scopeTypeKey = {
        inputName: 'scopeTypeKey',
        field: $scope.action.scopeTypeKey,
        fieldTitle: "REDUCTION_ACTION_SCOPE_TYPE_FIELD_TITLE"
      };
      $scope.scopeId = {
        inputName: 'scopeId',
        field: !!$scope.action.scopeId && ($scope.action.scopeTypeKey === "2") ? $scope.action.scopeId : $scope.$root.mySites[0].id
      };
      $scope.physicalMeasure = {
        inputName: 'physicalMeasure',
        field: $scope.action.physicalMeasure,
        fieldTitle: "REDUCTION_ACTION_PHYSICAL_MEASURE_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_PHYSICAL_MEASURE_FIELD_PLACEHOLDER",
        validationRegex: "^.{0,255}$",
        validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS",
        hideIsValidIcon: true
      };
      $scope.ghgBenefit = {
        inputName: 'ghgBenefit',
        field: $scope.action.ghgBenefit,
        numbersOnly: 'double',
        fieldTitle: "REDUCTION_ACTION_GHG_BENEFIT_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_GHG_BENEFIT_FIELD_PLACEHOLDER"
      };
      $scope.ghgBenefitUnitKey = (_ref = $scope.action.ghgBenefitUnitKey) != null ? _ref : "U5335";
      $scope.financialBenefit = {
        inputName: 'financialBenefit',
        field: $scope.action.financialBenefit,
        numbersOnly: 'double',
        fieldTitle: "REDUCTION_ACTION_FINANCIAL_BENEFIT_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_FINANCIAL_BENEFIT_FIELD_PLACEHOLDER"
      };
      $scope.investmentCost = {
        inputName: 'investment',
        field: $scope.action.investmentCost,
        numbersOnly: 'double',
        fieldTitle: "REDUCTION_ACTION_INVESTMENT_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_INVESTMENT_FIELD_PLACEHOLDER"
      };
      $scope.expectedPaybackTime = {
        inputName: 'expectedPaybackTime',
        field: $scope.action.expectedPaybackTime,
        validationRegex: "^.{0,255}$",
        fieldTitle: "REDUCTION_ACTION_EXPECTED_PAYBACK_TIME_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_EXPECTED_PAYBACK_TIME_FIELD_PLACEHOLDER",
        validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS",
        hideIsValidIcon: true
      };
      $scope.dueDate = {
        inputName: 'dueDate',
        field: (_ref2 = $scope.action.dueDate) != null ? _ref2 : $scope.getDefaultDueDate(),
        fieldTitle: "REDUCTION_ACTION_DUE_DATE_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_DUE_DATE_FIELD_PLACEHOLDER",
        minValue: new Date(),
        validationMessage: "REDUCTION_ACTION_INVALID_DUE_DATE",
        hideIsValidIcon: true
      };
      $scope.webSite = {
        inputName: 'webSite',
        field: $scope.action.webSite,
        validationRegex: "^.{0,255}$",
        fieldTitle: "REDUCTION_ACTION_WEBSITE_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_WEBSITE_FIELD_PLACEHOLDER",
        validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS",
        hideIsValidIcon: true
      };
      $scope.responsiblePerson = {
        inputName: 'responsiblePerson',
        field: $scope.action.responsiblePerson,
        validationRegex: "^.{0,255}$",
        fieldTitle: "REDUCTION_ACTION_RESPONSIBLE_PERSON_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_RESPONSIBLE_PERSON_FIELD_PLACEHOLDER",
        validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS",
        hideIsValidIcon: true
      };
      $scope.comment = {
        inputName: 'comment',
        field: $scope.action.comment,
        fieldType: 'textarea',
        validationRegex: ".{0,1000}",
        fieldTitle: "REDUCTION_ACTION_COMMENT_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_COMMENT_FIELD_PLACEHOLDER",
        validationMessage: "TEXT_FIELD_MAX_1000_CHARACTERS",
        hideIsValidIcon: true
      };
      $scope.files = (_ref3 = $scope.action.files) != null ? _ref3 : [];
      $scope.allFieldValid = function() {
        return $scope.title.isValid && $scope.physicalMeasure.isValid && $scope.expectedPaybackTime.isValid && $scope.dueDate.isValid && $scope.webSite.isValid && $scope.responsiblePerson.isValid && $scope.comment.isValid;
      };
      $scope.save = function() {
        var data;
        $scope.isLoading = true;
        data = {
          id: $scope.action.id,
          title: $scope.title.field,
          typeKey: $scope.typeKey.field,
          statusKey: $scope.statusKey.field,
          completionDate: $scope.action.completionDate,
          scopeTypeKey: $scope.scopeTypeKey.field,
          scopeId: $scope.scopeTypeKey.field === "1" ? null : $scope.scopeId.field,
          physicalMeasure: $scope.physicalMeasure.field,
          ghgBenefit: $scope.ghgBenefit.field,
          ghgBenefitUnitKey: $scope.ghgBenefitUnitKey,
          financialBenefit: $scope.financialBenefit.field,
          investmentCost: $scope.investmentCost.field,
          expectedPaybackTime: $scope.expectedPaybackTime.field,
          dueDate: $scope.dueDate.field,
          webSite: $scope.webSite.field,
          responsiblePerson: $scope.responsiblePerson.field,
          comment: $scope.comment.field,
          files: $scope.files
        };
        downloadService.postJson('/awac/actions/save', data, function(result) {
          if (result.success) {
            messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
            if ($scope.editMode) {
              angular.extend($scope.getParams().action, result.data);
            }
            $scope.close();
            if (!!$scope.getParams().cb) {
              return $scope.getParams().cb();
            }
          } else {
            return $scope.isLoading = false;
          }
        });
        return false;
      };
      $scope.toggleGhgBenefitField = function(typeKey) {
        if (typeKey === '2') {
          $scope.ghgBenefit.field = "";
          $scope.ghgBenefit.disabled = true;
          return $scope.ghgBenefitUnitKey.disabled = true;
        } else {
          $scope.ghgBenefit.disabled = false;
          return $scope.ghgBenefitUnitKey.disabled = false;
        }
      };
      $scope.$watch('typeKey.field', function(n, o) {
        if (n !== o) {
          return $scope.toggleGhgBenefitField(n);
        }
      });
      $scope.close = function() {
        return modalService.close(modalService.CREATE_REDUCTION_ACTION);
      };
      $scope.toggleGhgBenefitField($scope.typeKey.field);
      $scope.onFileSelect = function($files) {
        var file, i;
        $scope.inDownload = true;
        i = 0;
        while (i < $files.length) {
          file = $files[i];
          $scope.upload = $upload.upload({
            url: "/awac/file/upload/",
            file: file
          }).progress(function(evt) {
            $scope.percent = parseInt(100.0 * evt.loaded / evt.total);
            return;
          }).success(function(data) {
            var fileName;
            $scope.percent = 0;
            $scope.inDownload = false;
            fileName = data.name;
            messageFlash.displaySuccess("The file " + fileName + " was upload successfully");
            $scope.files.push(data);
            console.log($scope.files);
            return;
          }).error(function(data, status, headers, config) {
            $scope.percent = 0;
            $scope.inDownload = false;
            messageFlash.displayError("The upload of the file was failed");
            return;
          });
          i++;
        }
        return;
      };
      $scope.download = function(storedFileId) {
        var url;
        url = '/awac/file/download/' + storedFileId;
        return $window.open(url);
      };
      $scope.remove = function(storedFileId) {
        var file, index, _ref;
        _ref = $scope.files;
        for (index in _ref) {
          file = _ref[index];
          if (file.id === storedFileId) {
            $scope.files.splice(index, 1);
            break;
          }
        }
        return;
      };
      return {
        link: function(scope) {}
      };
    }
  };
});angular.module('app.directives').directive("mmAwacModalVerificationAssign", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-verification-assign.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.isLoading = true;
      $scope.usersSelected = [];
      downloadService.getJson('awac/organization/getMyOrganization', function(result) {
        var user, userToTest, _i, _j, _len, _len2, _ref, _ref2;
        if (!result.success) {
          messageFlash.displayError(result.data.message);
          return $scope.isLoading = false;
        } else {
          $scope.users = result.data.users;
          if ($scope.getParams().request.verifierList != null) {
            _ref = $scope.getParams().request.verifierList;
            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              user = _ref[_i];
              _ref2 = $scope.users;
              for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
                userToTest = _ref2[_j];
                if (user.identifier === userToTest.identifier) {
                  userToTest.selected = true;
                }
              }
              $scope.usersSelected.push(user);
            }
          }
          return $scope.isLoading = false;
        }
      });
      $scope.$watch('users', function() {
        var user, _i, _len, _ref, _results;
        $scope.usersSelected = [];
        if ($scope.users != null) {
          _ref = $scope.users;
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            user = _ref[_i];
            _results.push(user.selected === true ? $scope.usersSelected.push(user) : void 0);
          }
          return _results;
        }
      }, true);
      $scope.allFieldValid = function() {
        return $scope.usersSelected.length > 0;
      };
      $scope.save = function() {
        var dto, user, usersSelectedIdentifier, _i, _len, _ref;
        if ($scope.allFieldValid() === true) {
          $scope.isLoading = true;
          usersSelectedIdentifier = [];
          _ref = $scope.usersSelected;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            user = _ref[_i];
            usersSelectedIdentifier.push(user.identifier);
          }
          dto = {
            newStatus: 'VERIFICATION_STATUS_VERIFICATION',
            scopeId: $scope.getParams().request.scope.id,
            periodKey: $scope.getParams().request.period.key,
            verifierIdentifier: usersSelectedIdentifier
          };
          return downloadService.postJson("/awac/verification/setStatus", dto, function(result) {
            if (!result.success) {
              return $scope.isLoading = false;
            } else {
              $scope.isLoading = false;
              $scope.getParams().request.status = 'VERIFICATION_STATUS_VERIFICATION';
              $scope.getParams().request.verifierList = $scope.usersSelected;
              return $scope.close();
            }
          });
        }
      };
      return $scope.close = function() {
        return modalService.close(modalService.VERIFICATION_ASSIGN);
      };
    }
  };
});angular.module('app.directives').directive("mmAwacModalEditEvent", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-edit-event.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.createNewEvent = true;
      if ($scope.getParams().event != null) {
        $scope.event = angular.copy($scope.getParams().event);
        $scope.createNewEvent = false;
      } else {
        $scope.event = {};
      }
      $scope.fields = {
        name: {
          fieldTitle: "NAME",
          field: $scope.event.name,
          validationRegex: "^.{1,255}$",
          validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH",
          focus: function() {
            return true;
          }
        },
        description: {
          fieldTitle: "DESCRIPTION",
          validationRegex: "^.{0,65000}$",
          validationMessage: "CONTROL_FIELD_DEFAULT_TEXT",
          field: $scope.event.description,
          fieldType: 'textarea',
          hideIsValidIcon: true
        }
      };
      $scope.allFieldValid = function() {
        var key, _i, _len, _ref;
        _ref = Object.keys($scope.fields);
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          key = _ref[_i];
          if (key !== '$$hashKey') {
            if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
              return false;
            }
          }
        }
        return true;
      };
      $scope.save = function() {
        var data;
        if ($scope.allFieldValid()) {
          data = {};
          data.id = $scope.event.id;
          data.name = $scope.fields.name.field;
          data.description = $scope.fields.description.field;
          data.period = $scope.getParams().period;
          $scope.isLoading = true;
          if ($scope.getParams().event != null) {
            data.id = $scope.getParams().event.id;
            downloadService.postJson('/awac/organization/events/save', data, function(result) {
              if (result.success) {
                messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
                $scope.getParams().event.name = $scope.fields.name.field;
                $scope.getParams().event.description = $scope.fields.description.field;
                return $scope.close();
              } else {
                return $scope.isLoading = false;
              }
            });
          } else {
            downloadService.postJson('/awac/organization/events/save', data, function(result) {
              if (result.success) {
                messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
                if (!$scope.getParams().events) {
                  $scope.getParams().events = [];
                }
                $scope.getParams().events.push(result.data);
                return $scope.close();
              } else {
                return $scope.isLoading = false;
              }
            });
          }
        }
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.EDIT_EVENT);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalRequestVerification", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-request-verification.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.fields = {
        email: {
          inputName: 'email',
          fieldTitle: "REQUEST_VERIFICATION_EMAIL_VERIFIER",
          placeholder: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_PLACEHOLDER",
          validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
          validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT",
          focus: function() {
            return true;
          }
        },
        comment: {
          fieldTitle: "MODAL_QUESTION_COMMENT_TITLE",
          fieldType: 'textarea',
          validationRegex: "^.{0,65000}$",
          validationMessage: "CONTROL_FIELD_DEFAULT_TEXT",
          hideIsValidIcon: true
        },
        myPhoneNumber: {
          fieldTitle: "MY_PHONE_NUMBER",
          validationRegex: "^.{0,20}$",
          validationMessage: "CONTROL_FIELD_DEFAULT_STRING",
          hideIsValidIcon: true
        },
        myPassword: {
          fieldType: "password",
          inputName: 'password',
          fieldTitle: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_TITLE",
          placeholder: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_PLACEHOLDER",
          validationRegex: "^\\S{5,20}$",
          validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
        }
      };
      $scope.allFieldValid = function() {
        var key, _i, _len, _ref;
        _ref = Object.keys($scope.fields);
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          key = _ref[_i];
          if (key !== '$$hashKey') {
            if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
              return false;
            }
          }
        }
        return true;
      };
      $scope.save = function() {
        var data;
        data = {};
        data.password = $scope.fields.myPassword.field;
        data.periodKey = $scope.$root.periodSelectedKey;
        data.scopeId = $scope.$root.scopeSelectedId;
        data.email = $scope.fields.email.field;
        data.comment = $scope.fields.comment.field;
        data.phoneNumber = $scope.fields.myPhoneNumber.field;
        console.log(data);
        $scope.isLoading = true;
        downloadService.postJson('/awac/verification/createRequest', data, function(result) {
          if (result.success) {
            messageFlash.displaySuccess(translationService.get("REQUEST_SEND"));
            if (!$scope.$root.verificationRequest) {
              $scope.$root.verificationRequest = {};
            }
            $scope.$root.verificationRequest.status = 'VERIFICATION_STATUS_WAIT_VERIFIER_REGISTRATION';
            return $scope.close();
          } else {
            return $scope.isLoading = false;
          }
        });
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.REQUEST_VERIFICATION);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalEmailChange", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-email-change.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.passwordInfo = {
        field: "",
        fieldType: "password",
        inputName: 'password',
        fieldTitle: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_TITLE",
        placeholder: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_PLACEHOLDER",
        validationRegex: "^\\S{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        hideIsValidIcon: true,
        focus: function() {
          return true;
        }
      };
      $scope.oldEmailInfo = {
        inputName: 'email',
        field: $scope.getParams().oldEmail,
        fieldTitle: "EMAIL_CHANGE_FORM_OLD_EMAIL_FIELD_TITLE",
        disabled: true
      };
      $scope.newEmailInfo = {
        inputName: 'email',
        field: "",
        fieldTitle: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_TITLE",
        placeholder: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_PLACEHOLDER",
        validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
        validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT",
        hideIsValidIcon: true
      };
      $scope.allFieldValid = function() {
        if ($scope.passwordInfo.isValid && $scope.newEmailInfo.isValid) {
          return true;
        }
        return false;
      };
      $scope.save = function() {
        var data;
        if (!$scope.allFieldValid()) {
          return false;
        }
        $scope.isLoading = true;
        data = {
          password: $scope.passwordInfo.field,
          newEmail: $scope.newEmailInfo.field
        };
        downloadService.postJson('/awac/user/email/save', data, function(result) {
          if (result.success) {
            messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
            $scope.close();
            if ($scope.getParams().cb != null) {
              return $scope.getParams().cb($scope.newEmailInfo.field);
            }
          } else {
            return $scope.isLoading = false;
          }
        });
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.EMAIL_CHANGE);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalVerificationReject", function(directiveService, downloadService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-verification-reject.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.close = function() {
        return modalService.close(modalService.VERIFICATION_REJECT);
      };
      $scope.comment = $scope.ngParams.comment;
      return $scope.save = function() {
        var data;
        if (!$scope.getParams().readOnly || $scope.getParams().readOnly === false) {
          data = {
            questionSetKey: $scope.getParams().questionSetCode,
            periodKey: $scope.$root.periodSelectedKey,
            scopeId: $scope.$root.scopeSelectedId,
            verification: {
              status: 'rejected',
              comment: $scope.comment
            }
          };
          return downloadService.postJson('/awac/verification/verify', data, function(result) {
            if (result.success) {
              $scope.getParams().refreshVerificationStatus(result.data);
              $scope.$root.$broadcast('TEST_CLOSING_VALIDATION');
              return $scope.close();
            }
          });
        }
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalConsultEvent", function(directiveService, downloadService, translationService, messageFlash, ngTableParams, $filter) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-consult-event.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      downloadService.getJson('/awac/organization/events/byOrganization/' + $scope.getParams().organizationCustomer.name, function(result) {
        if (!result.success) {
          return $scope.isLoading = false;
        } else {
          $scope.events = result.data.organizationEventList;
          if (($scope.events != null) && $scope.events.length > 0) {
            if ($scope.tableParams != null) {
              $scope.tableParams.reload();
            } else {

            }
            $scope.tableParams = new ngTableParams({
              page: 1,
              count: 100
            }, {
              counts: [],
              total: 1,
              getData: function($defer, params) {
                var orderedData;
                orderedData = $filter("orderBy")($scope.events, params.orderBy());
                $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                return params.total(orderedData.length);
              }
            });
          }
          return $scope.isLoading = false;
        }
      });
      return $scope.close = function() {
        return modalService.close(modalService.CONSULT_EVENT);
      };
    }
  };
});angular.module('app.directives').directive("mmAwacModalAddUserSite", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-add-user-site.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      if ($scope.getParams().site != null) {
        $scope.site = angular.copy($scope.getParams().site);
        $scope.createNewSite = false;
      } else {
        $scope.site = {};
        $scope.site.percentOwned = 100;
      }
      $scope.allFieldValid = function() {
        return true;
      };
      $scope.save = function() {
        var data;
        if ($scope.allFieldValid()) {
          $scope.isLoading = true;
          data = {
            organization: $scope.getParams().organization,
            site: $scope.getParams().site,
            selectedAccounts: $scope.selection
          };
          downloadService.postJson('/awac/organization/site/associatedaccounts/save', data, function(result) {
            var account, founded, i, ownSite, site, _i, _j, _len, _len2, _ref, _ref2;
            if (result.success) {
              ownSite = false;
              _ref = $scope.selection;
              for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                account = _ref[_i];
                if (account.identifier === $scope.$root.currentPerson.identifier) {
                  ownSite = true;
                  break;
                }
              }
              i = 0;
              founded = false;
              _ref2 = $scope.$root.mySites;
              for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
                site = _ref2[_j];
                if (parseFloat(site.id) === parseFloat($scope.getParams().site.id)) {
                  founded = true;
                  if (ownSite === false) {
                    $scope.$root.mySites.splice(i, 1);
                  }
                  break;
                }
                i++;
              }
              if (ownSite === true && founded === false) {
                $scope.$root.mySites.push($scope.getParams().site);
              }
              return $scope.close();
            } else {
              messageFlash.displayError(result.data.message);
              return $scope.isLoading = false;
            }
          });
        }
        return false;
      };
      $scope.accounts = [];
      $scope.selection = [];
      $scope.toggleSelection = function(account) {
        var idx;
        console.log("entering toggleSelection");
        console.log(account.identifier);
        idx = $scope.selection.indexOf(account);
        console.log(idx);
        if (idx > -1) {
          return $scope.selection.splice(idx, 1);
        } else {
          return $scope.selection.push(account);
        }
      };
      $scope.close = function() {
        return modalService.close(modalService.ADD_USER_SITE);
      };
      return $scope.getAssociatedUsers = function() {
        var data;
        console.log("entering getAssociatedUsers");
        $scope.selection = [];
        $scope.accounts = [];
        $scope.prepare = [];
        data = {
          organization: $scope.getParams().organization,
          site: $scope.getParams().site
        };
        downloadService.postJson('/awac/organization/site/associatedaccounts/load', data, function(result) {
          var selected, user, _i, _j, _k, _len, _len2, _len3, _ref, _ref2, _ref3, _results;
          if (result.success) {
            _ref = result.data.organizationUserList;
            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              user = _ref[_i];
              $scope["in"] = false;
              if (result.data.siteSelectedUserList.length) {
                _ref2 = result.data.siteSelectedUserList;
                for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
                  selected = _ref2[_j];
                  if (user.identifier === selected.identifier) {
                    $scope["in"] = true;
                  }
                }
              }
              if ($scope["in"] === false) {
                $scope.accounts.push(user);
              }
            }
            _ref3 = result.data.siteSelectedUserList;
            _results = [];
            for (_k = 0, _len3 = _ref3.length; _k < _len3; _k++) {
              selected = _ref3[_k];
              $scope.selection.push(selected);
              _results.push($scope.accounts.push(selected));
            }
            return _results;
          }
        });
        return $scope.isLoading = false;
      };
    },
    link: function(scope) {
      console.log("entering mmAwacModalAddUserSite");
      return scope.getAssociatedUsers();
    }
  };
});angular.module('app.directives').directive("mmAwacModalVerificationConfirmation", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-verification-confirmation.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.fields = {
        myPassword: {
          fieldType: "password",
          inputName: 'password',
          fieldTitle: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_TITLE",
          placeholder: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_PLACEHOLDER",
          validationRegex: "^\\S{5,20}$",
          validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
          hideIsValidIcon: true,
          focus: function() {
            return true;
          }
        }
      };
      $scope.allFieldValid = function() {
        var key, _i, _len, _ref;
        _ref = Object.keys($scope.fields);
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          key = _ref[_i];
          if (key !== '$$hashKey') {
            if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
              return false;
            }
          }
        }
        return true;
      };
      $scope.save = function(valid) {
        var dto;
        if ($scope.allFieldValid()) {
          $scope.isLoading = true;
          dto = {
            scopeId: $scope.$root.scopeSelectedId,
            periodKey: $scope.$root.periodSelectedKey,
            password: $scope.fields.myPassword.field
          };
          if (valid === true) {
            dto.newStatus = 'VERIFICATION_STATUS_WAIT_ASSIGNATION';
          } else {
            dto.newStatus = 'VERIFICATION_STATUS_REJECTED';
          }
          return downloadService.postJson("/awac/verification/setStatus", dto, function(result) {
            $scope.isLoading = false;
            if (result.success) {
              if (valid === true) {
                $scope.$root.verificationRequest.status = 'VERIFICATION_STATUS_WAIT_ASSIGNATION';
              } else {
                $scope.$root.verificationRequest = null;
              }
              return $scope.close();
            }
          });
        }
      };
      return $scope.close = function() {
        return modalService.close(modalService.VERIFICATION_CONFIRMATION);
      };
    }
  };
});angular.module('app.directives').directive("mmAwacModalVerificationFinalization", function(directiveService, downloadService, translationService, messageFlash, $upload) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-verification-finalization.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.comment = {
        fieldTitle: "MODAL_QUESTION_COMMENT_TITLE",
        validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH",
        fieldType: 'textarea',
        hidden: $scope.getParams().verificationSuccess === true,
        focus: function() {
          return true;
        }
      };
      $scope.save = function() {
        var dto;
        if ($scope.allFieldValid()) {
          $scope.isLoading = true;
          dto = {
            scopeId: $scope.getParams().request.scope.id,
            periodKey: $scope.getParams().request.period.key
          };
          if ($scope.getParams().verificationSuccess === true) {
            dto.newStatus = 'VERIFICATION_STATUS_WAIT_VERIFICATION_CONFIRMATION_SUCCESS';
            dto.verificationFinalizationFileId = $scope.file.id;
          } else {
            dto.newStatus = 'VERIFICATION_STATUS_WAIT_VERIFICATION_CONFIRMATION_REJECT';
            dto.verificationRejectedComment = $scope.comment.field;
          }
          return downloadService.postJson("/awac/verification/setStatus", dto, function(result) {
            $scope.isLoading = false;
            if (result.success) {
              $scope.getParams().removeRequest();
              return $scope.close();
            }
          });
        }
      };
      $scope.onFileSelect = function($files) {
        var file, i;
        $scope.inDownload = true;
        i = 0;
        while (i < $files.length) {
          file = $files[i];
          $scope.upload = $upload.upload({
            url: "/awac/file/upload/",
            data: {
              myObj: $scope.myModelObj
            },
            file: file
          }).progress(function(evt) {
            $scope.percent = parseInt(100.0 * evt.loaded / evt.total);
            return;
          }).success(function(data, status, headers, config) {
            var fileName;
            $scope.percent = 0;
            $scope.inDownload = false;
            fileName = data.name;
            messageFlash.displaySuccess("The file " + fileName + " was upload successfully");
            $scope.file = data;
            return;
          }).error(function(data, status, headers, config) {
            $scope.percent = 0;
            $scope.inDownload = false;
            messageFlash.displayError("The upload of the file was faild");
            return;
          });
          i++;
        }
        return;
      };
      $scope.allFieldValid = function() {
        return $scope.getParams().verificationSuccess !== true || ($scope.file != null);
      };
      return $scope.close = function() {
        return modalService.close(modalService.VERIFICATION_FINALIZATION);
      };
    }
  };
});angular.module('app.directives').directive("mmAwacModalManager", function(directiveService, $compile) {
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
          scope.displayModal(args.target, args.params);
        } else {
          scope.removeModal(args.target);
        }
        return;
      });
      scope.displayModal = function(target, params) {
        var directive, paramName;
        paramName = 'params_' + target.replace(/-/g, "_");
        scope[paramName] = params;
        directive = $compile("<mm-awac-modal-" + target + " ng-params=\"" + paramName + "\" ></mm-awac-modal-" + target + ">")(scope);
        element.append(directive);
        return;
      };
      return scope.removeModal = function(target) {
        var child, _i, _len, _ref;
        _ref = element.children();
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          child = _ref[_i];
          if (child.tagName.toLowerCase() === 'mm-awac-modal-' + target.toLowerCase()) {
            angular.element(child).remove();
          }
        }
        return;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacModalVerificationDocument", function(directiveService, downloadService, translationService, messageFlash, $window) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-verification-document.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.fields = {
        myPassword: {
          fieldType: "password",
          inputName: 'password',
          fieldTitle: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_TITLE",
          placeholder: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_PLACEHOLDER",
          validationRegex: "^\\S{5,20}$",
          validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
          hideIsValidIcon: true,
          focus: function() {
            return true;
          }
        }
      };
      $scope.download = function() {
        var url;
        url = '/awac/file/download/' + $scope.$root.verificationRequest.verificationSuccessFileId;
        return $window.open(url);
      };
      $scope.allFieldValid = function() {
        var key, _i, _len, _ref;
        _ref = Object.keys($scope.fields);
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          key = _ref[_i];
          if (key !== '$$hashKey') {
            if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
              return false;
            }
          }
        }
        return true;
      };
      $scope.save = function(valid) {
        var data, newStatus;
        if ($scope.allFieldValid()) {
          $scope.isLoading = true;
          if (valid === true) {
            newStatus = 'VERIFICATION_STATUS_VERIFIED';
          } else {
            newStatus = 'VERIFICATION_STATUS_VERIFICATION';
          }
          data = {
            scopeId: $scope.$root.scopeSelectedId,
            periodKey: $scope.$root.periodSelectedKey,
            password: $scope.fields.myPassword.field,
            newStatus: newStatus
          };
          downloadService.postJson("/awac/verification/setStatus", data, function(result) {
            var _ref;
            $scope.isLoading = false;
            if (result.success) {
              $scope.$root.testCloseable();
              if ((_ref = $scope.$root.verificationRequest) != null) {
                _ref.status = newStatus;
              }
              return $scope.close();
            }
          });
        }
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.VERIFICATION_DOCUMENT);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalConnectionPasswordChange", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-connection-password-change.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.validatePasswordConfirmField = function() {
        if ($scope.newPasswordConfirmInfo.field.match("^\\S{5,20}$")) {
          if ($scope.newPasswordInfo.field === $scope.newPasswordConfirmInfo.field) {
            return true;
          }
          $scope.newPasswordConfirmInfo.validationMessage = "PASSWORD_VALIDATION_WRONG_CONFIRMATION";
        } else {
          $scope.newPasswordConfirmInfo.validationMessage = "PASSWORD_VALIDATION_WRONG_LENGTH";
        }
        return false;
      };
      $scope.newPasswordInfo = {
        inputName: 'password',
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_TITLE",
        fieldType: "password",
        placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_PLACEHOLDER",
        validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        hideIsValidIcon: true,
        field: "",
        focus: function() {
          return true;
        }
      };
      $scope.newPasswordConfirmInfo = {
        inputName: 'password',
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE",
        fieldType: "password",
        placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_PLACEHOLDER",
        validationFct: $scope.validatePasswordConfirmField,
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        hideIsValidIcon: true,
        field: ""
      };
      $scope.allFieldValid = function() {
        if ($scope.newPasswordInfo.isValid && $scope.newPasswordConfirmInfo.isValid) {
          return true;
        }
        return false;
      };
      $scope.save = function() {
        var data;
        if (!$scope.allFieldValid()) {
          return false;
        }
        $scope.isLoading = true;
        data = {
          login: $scope.getParams().login,
          password: $scope.getParams().password,
          interfaceName: $scope.$root.instanceName,
          newPassword: $scope.newPasswordInfo.field
        };
        downloadService.postJson('/awac/login', data, function(result) {
          if (result.success) {
            $scope.$root.loginSuccess(result.data);
            messageFlash.displaySuccess(translationService.get('CONNECTION_MESSAGE_SUCCESS'));
            $scope.isLoading = false;
            return $scope.close();
          } else {
            return $scope.isLoading = false;
          }
        });
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.CONNECTION_PASSWORD_CHANGE);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalConfirmClosing", function(directiveService, downloadService, translationService, messageFlash, $filter) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-confirm-closing.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.isLoading = false;
      $scope.close = function() {
        return modalService.close(modalService.CONFIRM_CLOSING);
      };
      $scope.passwordInfo = {
        fieldTitle: "USER_PASSWORD",
        fieldType: "password",
        placeholder: "PASSWORD_CHANGE_FORM_OLD_PASSWORD_FIELD_PLACEHOLDER",
        validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        field: "",
        hideIsValidIcon: true,
        focus: function() {
          return true;
        }
      };
      $scope.allFieldValid = function() {
        if ($scope.passwordInfo.isValid) {
          return true;
        }
        return false;
      };
      return $scope.valid = function() {
        var close, dto;
        if ($scope.allFieldValid()) {
          close = !$scope.$root.closedForms;
          $scope.isLoading = true;
          dto = {};
          dto.password = $scope.passwordInfo.field;
          dto.periodKey = $scope.$root.periodSelectedKey;
          dto.scopeId = $scope.$root.scopeSelectedId;
          dto.close = close;
          return downloadService.postJson("/awac/answer/closeForm/", dto, function(result) {
            if (result.success) {
              $scope.$root.closedForms = close;
              if (close) {
                messageFlash.displaySuccess($filter('translate')('MODAL_CONFIRM_CLOSING_SUCCESS'));
              } else {
                messageFlash.displaySuccess($filter('translate')('MODAL_CONFIRM_UNCLOSING_SUCCESS'));
              }
              $scope.isLoading = false;
              return $scope.close();
            } else {
              return $scope.isLoading = false;
            }
          });
        }
      };
    }
  };
});angular.module('app.directives').directive("mmAwacModalDocumentManager", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-document-manager.html",
    controller: function($scope, modalService, $location, $window) {
      directiveService.autoScopeImpl($scope);
      $scope.listDocuments = $scope.getParams().listDocuments;
      console.log($scope.listDocuments);
      $scope.show = false;
      $scope.loc = null;
      $scope.download = function(storedFileId) {
        var url;
        url = '/awac/file/download/' + storedFileId;
        return $window.open(url);
      };
      $scope.removeDoc = function(storedFileId) {
        if ($scope.getParams().readyOnly !== true) {
          delete $scope.listDocuments[storedFileId];
          return $scope.getParams().wasEdited();
        }
      };
      return $scope.close = function() {
        return modalService.close(modalService.DOCUMENT_MANAGER);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalConfirmDialog", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-confirm-dialog.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.titleKey = $scope.getParams().titleKey;
      $scope.messageKey = $scope.getParams().messageKey;
      $scope.close = function() {
        return modalService.close(modalService.CONFIRM_DIALOG);
      };
      return $scope.confirm = function() {
        if (!!$scope.getParams().onConfirm) {
          $scope.getParams().onConfirm();
        }
        return $scope.close();
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalVerificationFinalizationVisualization", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-verification-finalization-visualization.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.close = function() {
        return modalService.close(modalService.VERIFICATION_FINALIZATION_VISUALIZATION);
      };
      return $scope.comment = $scope.ngParams.comment;
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalLoading", function(directiveService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-modal-loading.html",
    controller: function($scope, modalService) {
      return $scope.close = function() {
        return modalService.close(modalService.LOADING);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalConfirmationExitForm", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-confirmation-exit-form.html",
    controller: function($scope, modalService) {
      directiveService.autoScope;
      $scope.close = function() {
        return modalService.close(modalService.CONFIRMATION_EXIT_FORM);
      };
      $scope["continue"] = function() {
        $scope.$root.nav($scope.ngParams.loc, true);
        return $scope.close();
      };
      return $scope.save = function() {
        var arg;
        arg = {};
        arg.loc = $scope.ngParams.loc;
        arg.confirmed = true;
        $scope.$root.$broadcast('SAVE_AND_NAV', arg);
        return $scope.close();
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmFieldDate", function(directiveService, $filter, generateId) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngInfo: '='
    }),
    templateUrl: "$/angular/templates/mm-field-date.html",
    replace: true,
    transclude: true,
    compile: function() {
      return {
        pre: function(scope) {
          scope.id = generateId.generate();
          return scope.idHtag = '#' + scope.id;
        },
        post: function(scope) {
          directiveService.autoScopeImpl(scope);
          scope.result = null;
          scope.$watch('result', function() {
            return scope.resultFormated = $filter('date')(scope.result, 'yyyy-MM-dd');
          });
          scope.$watch('getInfo().field', function() {
            if (scope.getInfo().field != null) {
              return scope.result = new Date(Number(scope.getInfo().field));
            }
          });
          scope.$watch('result', function() {
            if (scope.result != null) {
              scope.getInfo().field = scope.result.getTime();
            } else {
              scope.getInfo().field = null;
            }
            return scope.isValid();
          });
          scope.isValid = function() {
            var isValid;
            console.log("Validating date = " + scope.getInfo().field);
            if (scope.getInfo().disabled === true || scope.getInfo().hidden === true) {
              scope.getInfo().isValid = true;
              return;
            }
            isValid = true;
            if (!(scope.getInfo().field != null)) {
              isValid = false;
            }
            if (scope.getInfo().minValue != null) {
              isValid = scope.getInfo().field > scope.getInfo().minValue.getTime();
            }
            if (scope.getInfo().maxValue != null) {
              isValid = scope.getInfo().field < scope.getInfo().maxValue.getTime();
            }
            scope.getInfo().isValid = isValid;
            return;
          };
          scope.isValid();
          return scope.logField = function() {
            return console.log(scope.getInfo());
          };
        }
      };
    }
  };
});angular.module('app.directives').directive("ngEnter", function() {
  return function(scope, element, attrs) {
    return element.bind("keydown keypress", function(event) {
      if (event.which === 13 && event.target.tagName !== 'TEXTAREA') {
        scope.$apply(function() {
          return scope.$eval(attrs.ngEnter);
        });
        return event.preventDefault();
      }
    });
  };
});angular.module('app.directives').directive("mmButton", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngCode: '=',
      ngIcon: '='
    }),
    templateUrl: "$/angular/templates/mm-button.html",
    replace: true,
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("focusMe", function($timeout, $parse) {
  return {
    restrict: 'A',
    scope: {
      focusMe: '='
    },
    link: function(scope, element, attrs) {
      scope.$watch('focusMe', function() {
        if (scope.focusMe === true) {
          return element[0].focus();
        }
      });
      return;
    }
  };
});angular.module('app.directives').directive("ngEscape", function() {
  return function(scope, element, attrs) {
    return element.bind("keydown keypress", function(event) {
      if (event.which === 27) {
        scope.$apply(function() {
          return scope.$eval(attrs.ngEscape);
        });
        return event.preventDefault();
      }
    });
  };
});angular.module('app.directives').directive("numbersOnly", function($filter, translationService, $locale) {
  return {
    restrict: 'A',
    require: "ngModel",
    link: function(scope, element, attrs, modelCtrl) {
      return scope.$watch(attrs.numbersOnly, function() {
        var convertToFloat, convertToString, displayError, errorMessage, filterFloat, nbDecimal, valueToDisplay;
        if (attrs.numbersOnly === "integer" || attrs.numbersOnly === "double" || attrs.numbersOnly === "percent") {
          scope.lastValidValue = "";
          if (attrs.numbersOnly === "integer") {
            errorMessage = $filter('translateText')('ONLY_INTEGER');
            nbDecimal = 0;
          } else {
            errorMessage = $filter('translateText')('ONLY_NUMBER');
            nbDecimal = 3;
          }
          scope.$root.$on('$localeChangeSuccess', function(event, current, previous) {
            var result;
            if (modelCtrl.$modelValue != null) {
              result = convertToString(parseFloat(modelCtrl.$modelValue));
              if (result != null) {
                modelCtrl.$setViewValue(result.toString());
                return modelCtrl.$render();
              }
            }
          });
          modelCtrl.$parsers.unshift(function(viewValue) {
            var result, resultString, resultToDisplay;
            if (viewValue === "") {
              return null;
            }
            result = convertToFloat(viewValue);
            if (isNaN(result)) {
              displayError();
              if (scope.lastValidValue != null) {
                resultString = scope.lastValidValue.toString();
                if (attrs.numbersOnly === "percent") {
                  resultToDisplay = (scope.lastValidValue * 100).toString();
                } else {
                  resultToDisplay = scope.lastValidValue.toString();
                }
              } else {
                resultString = "";
                resultToDisplay = "";
              }
              modelCtrl.$setViewValue(resultToDisplay);
              modelCtrl.$render();
            } else {
              if (attrs.numbersOnly === "percent") {
                result = result / 100;
              }
              scope.lastValidValue = result;
              resultString = result.toString();
            }
            if (resultString === "") {
              return null;
            }
            return resultString;
          });
          modelCtrl.$formatters.unshift(function(modelValue) {
            return scope.displayValue(modelValue);
          });
          scope.displayValue = function(modelValue) {
            var result;
            result = parseFloat(modelValue);
            if (attrs.numbersOnly === "percent") {
              result = result * 100;
            }
            return convertToString(result);
          };
          displayError = function() {
            if (scope.setErrorMessage != null) {
              return scope.setErrorMessage(errorMessage);
            }
          };
          convertToString = function(value) {
            var formats, result;
            if (!(value != null) || isNaN(value)) {
              return "";
            }
            value = value.toFixed(nbDecimal);
            formats = $locale.NUMBER_FORMATS;
            return result = value.toString().replace(new RegExp("\\.", "g"), formats.DECIMAL_SEP);
          };
          convertToFloat = function(viewValue) {
            var decimalRegex, formats, value;
            if (viewValue === "") {
              return NaN;
            }
            formats = $locale.NUMBER_FORMATS;
            decimalRegex = formats.DECIMAL_SEP;
            if (decimalRegex === ".") {
              decimalRegex = "\\.";
            }
            value = viewValue.replace(new RegExp(decimalRegex, "g"), ".");
            return filterFloat(value);
          };
          filterFloat = function(value) {
            var regexFloat;
            if (value.isNaN) {
              return NaN;
            }
            if (attrs.numbersOnly === "integer") {
              regexFloat = new RegExp("^(\\-|\\+)?([0-9]+|Infinity)?$");
            } else {
              regexFloat = new RegExp("^(\\-|\\+)?([0-9]+(\\.[0-9]*)?|Infinity)?$");
            }
            if (regexFloat.test(value)) {
              return Number(value);
            }
            return NaN;
          };
          if (modelCtrl.$modelValue != null) {
            scope.lastValidValue = parseFloat(modelCtrl.$modelValue);
            valueToDisplay = scope.displayValue(scope.lastValidValue);
            modelCtrl.$setViewValue(valueToDisplay);
            return modelCtrl.$render();
          }
        }
      });
    }
  };
});angular.module('app.directives').directive("mmAwacTabProgressBar", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngValue: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-tab-progress-bar.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.pg = null;
      scope.color = null;
      return scope.$watch('value', function(v) {
        if (v < 0) {
          v = 0;
        }
        if (v > 100) {
          v = 100;
        }
        if (v >= 66) {
          scope.color = 'green';
        } else {
          if (v >= 33) {
            scope.color = 'orange';
          } else {
            scope.color = 'red';
          }
        }
        return scope.pg = v;
      });
    }
  };
});angular.module('app.directives').directive("mmAwacGraphDonut", function($sce, $filter) {
  return {
    restrict: "E",
    scope: {
      ngItems: '='
    },
    templateUrl: "$/angular/templates/mm-awac-graph-donut.html",
    replace: true,
    link: function(scope, element) {
      scope.legend = '';
      return scope.$watch('ngItems', function() {
        var color, colorh, ctx, d, data, f, i, myDoughnutChart, total, _len, _len2;
        if (scope.ngItems != null) {
          ctx = $('.holder', element).get(0).getContext('2d');
          data = angular.copy(scope.ngItems);
          f = $filter('numberToI18N');
          total = 0;
          for (i = 0, _len = data.length; i < _len; i++) {
            d = data[i];
            total += d.value;
          }
          for (i = 0, _len2 = data.length; i < _len2; i++) {
            d = data[i];
            color = tinycolor({
              h: 360.0 * i / data.length,
              s: 0.5,
              l: 0.5
            }).toHexString(true);
            colorh = tinycolor({
              h: 360.0 * i / data.length,
              s: 0.75,
              l: 0.66
            }).toHexString(true);
            d.color = color;
            d.highlight = colorh;
            d.label += ' (<b>' + f(100.0 * d.value / total) + '%</b>)';
          }
          myDoughnutChart = new Chart(ctx).Doughnut(data, {
            tooltipTemplate: function(o) {
              return f(100.0 * (o.endAngle - o.startAngle) / (Math.PI * 2)) + "%&nbsp;(" + f(o.value) + "%&nbsp;tCO2e)";
            },
            animation: false,
            legendTemplate: "" + "<% for (var i=0; i<segments.length; i++){%>" + "<div><span class=\"chart-legend-bullet-color\" style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></div>" + "<%}%>"
          });
          return scope.legend = $sce.trustAsHtml(myDoughnutChart.generateLegend());
        }
      });
    }
  };
});angular.module('app.directives').directive("mmAwacRegistrationMunicipality", function(directiveService, downloadService, messageFlash) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-registration-municipality.html",
    replace: true,
    controller: function($scope) {
      $scope.validatePasswordConfirmField = function() {
        if ((($scope.passwordInfo.isValid != null) === true && true > 0) && $scope.passwordInfo.field === $scope.passwordConfirmInfo.field) {
          return true;
        }
        return false;
      };
      $scope.identifierInfo = {
        fieldTitle: "USER_IDENTIFIER",
        inputName: 'identifier',
        validationRegex: "[a-zA-Z0-9-]{5,20}",
        validationMessage: "IDENTIFIER_CHECK_WRONG"
      };
      $scope.lastNameInfo = {
        fieldTitle: "USER_LASTNAME",
        inputName: 'lastName',
        validationRegex: "^.{1,255}$",
        validationMessage: "USER_LASTNAME_WRONG_LENGTH"
      };
      $scope.firstNameInfo = {
        fieldTitle: "USER_FIRSTNAME",
        inputName: 'firstName',
        fieldType: "text",
        validationRegex: "^.{1,255}$",
        validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
        focus: function() {
          return $scope.$parent.tabActive[2];
        }
      };
      $scope.emailInfo = {
        fieldTitle: "USER_EMAIL",
        inputName: 'email',
        validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
        validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
      };
      $scope.passwordInfo = {
        fieldTitle: "USER_PASSWORD",
        inputName: 'password',
        fieldType: "password",
        validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
      };
      $scope.passwordConfirmInfo = {
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE",
        inputName: 'password',
        fieldType: "password",
        validationFct: $scope.validatePasswordConfirmField,
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
      };
      $scope.municipalityNameInfo = {
        fieldTitle: "MUNICIPALITY_NAME",
        fieldType: "text",
        validationRegex: "^.{1,255}$",
        validationMessage: "MUNICIPALITY_NAME_WRONG_LENGTH"
      };
      $scope.organizationStatisticsAllowed = true;
      $scope.registrationFieldValid = function() {
        if ($scope.identifierInfo.isValid && $scope.lastNameInfo.isValid && $scope.firstNameInfo.isValid && $scope.emailInfo.isValid && $scope.passwordInfo.isValid && $scope.passwordConfirmInfo.isValid && $scope.municipalityNameInfo.isValid) {
          return true;
        }
        return false;
      };
      return $scope.registration = function() {
        var data;
        $scope.isLoading = true;
        data = {};
        data.person = {};
        data.person.email = $scope.emailInfo.field;
        data.person.identifier = $scope.identifierInfo.field;
        data.person.firstName = $scope.firstNameInfo.field;
        data.person.lastName = $scope.lastNameInfo.field;
        data.password = $scope.passwordInfo.field;
        data.organizationName = $scope.municipalityNameInfo.field;
        data.organizationStatisticsAllowed = $scope.organizationStatisticsAllowed;
        data.person.defaultLanguage = $scope.$root.language;
        downloadService.postJson('/awac/registration/municipality', data, function(result) {
          if (result.success) {
            $scope.$root.loginSuccess(result.data);
            messageFlash.displaySuccess(translationService.get("CONNECTION_MESSAGE_SUCCESS"));
            return $scope.isLoading = false;
          } else {
            return $scope.isLoading = false;
          }
        });
        return false;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacRegistrationEnterprise", function(directiveService, downloadService, messageFlash, translationService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-registration-enterprise.html",
    replace: true,
    controller: function($scope) {
      $scope.validatePasswordConfirmField = function() {
        if ((($scope.passwordInfo.isValid != null) === true && true > 0) && $scope.passwordInfo.field === $scope.passwordConfirmInfo.field) {
          return true;
        }
        return false;
      };
      $scope.identifierInfo = {
        fieldTitle: "USER_IDENTIFIER",
        inputName: 'identifier',
        validationRegex: "[a-zA-Z0-9-]{5,20}",
        validationMessage: "IDENTIFIER_CHECK_WRONG"
      };
      $scope.lastNameInfo = {
        fieldTitle: "USER_LASTNAME",
        inputName: 'lastName',
        validationRegex: "^.{1,255}$",
        validationMessage: "USER_LASTNAME_WRONG_LENGTH"
      };
      $scope.firstNameInfo = {
        fieldTitle: "USER_FIRSTNAME",
        inputName: 'firstName',
        fieldType: "text",
        validationRegex: "^.{1,255}$",
        validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
        focus: function() {
          return $scope.$parent.tabActive[2];
        }
      };
      $scope.emailInfo = {
        fieldTitle: "USER_EMAIL",
        inputName: 'email',
        validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
        validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
      };
      $scope.passwordInfo = {
        fieldTitle: "USER_PASSWORD",
        inputName: 'password',
        fieldType: "password",
        validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
      };
      $scope.passwordConfirmInfo = {
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE",
        inputName: 'password',
        fieldType: "password",
        validationFct: $scope.validatePasswordConfirmField,
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
      };
      $scope.organizationNameInfo = {
        fieldTitle: "ORGANIZATION_NAME",
        fieldType: "text",
        validationRegex: "^.{1,255}$",
        validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH"
      };
      $scope.organizationStatisticsAllowed = true;
      $scope.firstSiteNameInfo = {
        fieldTitle: "MAIN_SITE_NAME",
        fieldType: "text",
        validationRegex: "^.{1,255}$",
        validationMessage: "SITE_NAME_WRONG_LENGTH"
      };
      $scope.registrationFieldValid = function() {
        if ($scope.identifierInfo.isValid && $scope.lastNameInfo.isValid && $scope.firstNameInfo.isValid && $scope.emailInfo.isValid && $scope.passwordInfo.isValid && $scope.passwordConfirmInfo.isValid && $scope.organizationNameInfo.isValid && $scope.firstSiteNameInfo.isValid) {
          return true;
        }
        return false;
      };
      return $scope.registration = function() {
        var data;
        $scope.isLoading = true;
        data = {};
        data.person = {};
        data.person.email = $scope.emailInfo.field;
        data.person.identifier = $scope.identifierInfo.field;
        data.person.firstName = $scope.firstNameInfo.field;
        data.person.lastName = $scope.lastNameInfo.field;
        data.password = $scope.passwordInfo.field;
        data.organizationName = $scope.organizationNameInfo.field;
        data.organizationStatisticsAllowed = $scope.organizationStatisticsAllowed;
        data.firstSiteName = $scope.firstSiteNameInfo.field;
        data.person.defaultLanguage = $scope.$root.language;
        downloadService.postJson('/awac/registration/enterprise', data, function(result) {
          if (result.success) {
            $scope.$root.loginSuccess(result.data);
            messageFlash.displaySuccess(translationService.get("CONNECTION_MESSAGE_SUCCESS"));
            return $scope.isLoading = false;
          } else {
            return $scope.isLoading = false;
          }
        });
        return false;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacVerificationForm", function(directiveService) {
  return {
    restrict: "E",
    templateUrl: function() {
      return "$/angular/views/enterprise/" + form + ".html";
    }
  };
});angular.module('app.directives').directive("mmAwacResultTable", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-result-table.html",
    replace: true,
    transclude: true,
    link: function(scope, element) {
      directiveService.autoScopeImpl(scope);
      scope.getLeftTotalScope1 = function() {
        var rl, total, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.leftScope1Value;
        }
        return total;
      };
      scope.getLeftTotalScope2 = function() {
        var rl, total, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.leftScope2Value;
        }
        return total;
      };
      scope.getLeftTotalScope3 = function() {
        var rl, total, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.leftScope3Value;
        }
        return total;
      };
      scope.getLeftTotalOutOfScope = function() {
        var rl, total, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.leftOutOfScopeValue;
        }
        return total;
      };
      scope.getRightTotalScope1 = function() {
        var rl, total, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.rightScope1Value;
        }
        return total;
      };
      scope.getRightTotalScope2 = function() {
        var rl, total, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.rightScope2Value;
        }
        return total;
      };
      scope.getRightTotalScope3 = function() {
        var rl, total, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.rightScope3Value;
        }
        return total;
      };
      return scope.getRightTotalOutOfScope = function() {
        var rl, total, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.rightOutOfScopeValue;
        }
        return total;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacCalculatorSurvey", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-calculator-survey.html",
    transclude: true,
    replace: true,
    controller: 'MainCtrl',
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("mmAwacVerificationSurvey", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-verification-survey.html",
    transclude: true,
    replace: true,
    controller: 'MainCtrl',
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("mmAwacAdminSurvey", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-admin-survey.html",
    transclude: true,
    replace: true,
    controller: 'MainCtrl',
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("mmAwacSubTitle", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-sub-title.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      return scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacQuestion", function(directiveService, translationService, $compile, $timeout, modalService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '=',
      ngCondition: '=',
      ngRepetitionMap: '=',
      ngAggregation: '=',
      ngTabSet: '=',
      ngTab: '=',
      ngOptional: '='
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
            var answerType, directive, directiveName, isAggregation, params, toCompare;
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
                  directiveName = "real-question";
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
                params = " ";
                params += " ng-data-to-compare=\"" + toCompare + "\"";
                params += " ng-is-aggregation=\"" + isAggregation + "\"";
                directive = $compile("<mm-awac-" + directiveName + params + " ></mm-awac-" + directiveName + ">")(scope);
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
              if ((scope.ngAggregation != null) && scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap(), scope.getTabSet(), scope.getTab()).value === null) {
                scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap(), scope.getTabSet(), scope.getTab(), scope.getOptional()).value = scope.getAggregation();
                scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap(), scope.getTabSet(), scope.getTab(), scope.getOptional()).isAggregation = true;
              }
              return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap(), scope.getTabSet(), scope.getTab(), scope.getOptional());
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
              return scope.getAnswer().lastUpdateUser = scope.$root.currentPerson;
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
                  if (scope.$parent.loading === false) {
                    scope.getAnswer().value = null;
                    scope.edited();
                    scope.$root.$broadcast('CONDITION');
                  } else {
                    scope.getAnswer().wasEdited = false;
                  }
                }
              }
              return false;
            }
            if ((elementToTest.parent() != null) && elementToTest.parent().length > 0 && elementToTest.parent()[0].tagName !== 'BODY') {
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
                user = scope.getAnswer(true).lastUpdateUser;
              }
            } else {
              if (scope.getAnswer() !== null) {
                user = scope.getAnswer().lastUpdateUser;
              }
            }
            if (user === null) {
              return "";
            }
            if (initialOnly) {
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
            answer = scope.getAnswer();
            if (answer.value !== null) {
              if (answer.wasEdited !== void 0 && answer.wasEdited === true) {
                return 'answer_temp';
              }
              return 'answer';
            } else {
              if (scope.getQuestion() !== null && scope.getOptional() === true) {
                return "";
              }
              if (answer.wasEdited !== void 0 && answer.wasEdited === true) {
                return 'pending_temp';
              }
              return 'pending';
            }
          };
          scope.copyDataToCompare = function() {
            if (scope.getAnswer(true) !== null) {
              scope.getAnswer().value = scope.getAnswer(true).value;
              if (scope.getAnswer(true).unitCode != null) {
                scope.getAnswer().unitCode = scope.getAnswer(true).unitCode;
              }
              if (scope.getAnswer(true).comment != null) {
                scope.getAnswer().comment = scope.getAnswer(true).comment;
              }
              return scope.edited();
            }
          };
          scope.logQuestionCode = function() {
            console.log(scope.getQuestionCode() + ",value:" + scope.getAnswer().value + ",wasEdited:" + scope.getAnswer().wasEdited);
            return console.log(scope.getAnswer());
          };
          scope.errorMessage = "";
          scope.getIcon = function() {
            if (scope.ngAggregation != null) {
              return 'glyphicon-cog';
            }
            if ((scope.getQuestion() != null) && scope.getQuestion().answerType === 'DOCUMENT') {
              return 'glyphicon-file';
            }
            if (scope.getOptional() === true) {
              return 'glyphicon-paperclip';
            }
            return 'glyphicon-share-alt';
          };
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
          scope.editComment = function(canBeEdited) {
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
          scope.isDisabled = function() {
            if (scope.$parent.isQuestionLocked(scope.getQuestionCode()) || scope.$parent.isQuestionValidate(scope.getQuestionCode()) || scope.$root.instanceName === 'verification') {
              return true;
            }
            return false;
          };
          return scope.getUserClass = function() {
            if (scope.getAnswer() !== null && (scope.$parent.getValidatorByQuestionCode(scope.getQuestionCode()) != null)) {
              if (scope.$parent.getValidatorByQuestionCode(scope.getQuestionCode()).identifier === scope.getAnswer().lastUpdateUser.identifier) {
                return 'user_icon_red';
              }
              return 'user_icon_green';
            }
            return '';
          };
        }
      };
    }
  };
});angular.module('app.directives').directive("mmAwacSection", function(directiveService, downloadService, messageFlash, modalService, $location, $filter) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngTitleCode: '=',
      ngMode: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-section.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.decorateMode = function(v) {
        if (v) {
          return 'element_' + v;
        } else {
          return 'element_table';
        }
      };
      scope.lock = function() {
        var data;
        if ((!scope.isLocked() || scope.isLockedByMyself() || scope.$root.currentPerson.isAdmin) && !scope.isValidate() && scope.$root.closedForms === false) {
          data = {};
          data.questionSetKey = scope.getTitleCode();
          data.periodCode = scope.$root.periodSelectedKey;
          data.scopeId = scope.$root.scopeSelectedId;
          data.lock = scope.isLocked() ? false : true;
          return downloadService.postJson('/awac/answer/lockQuestionSet', data, function(result) {
            if (result.success) {
              return scope.$parent.lockQuestionSet(scope.getTitleCode(), data.lock);
            }
          });
        }
      };
      scope.isLockedByMyself = function() {
        if (scope.$parent.getQuestionSetLocker(scope.getTitleCode()) != null) {
          if (scope.$parent.getQuestionSetLocker(scope.getTitleCode()).identifier === scope.$root.currentPerson.identifier) {
            return true;
          }
        }
        return false;
      };
      scope.isLocked = function() {
        if (scope.$parent.getQuestionSetLocker(scope.getTitleCode()) != null) {
          return true;
        }
        return false;
      };
      scope.getLocker = function() {
        return scope.$parent.getQuestionSetLocker(scope.getTitleCode());
      };
      scope.getLockClass = function() {
        if (scope.isLocked()) {
          if ((scope.isLockedByMyself() || scope.$root.currentPerson.isAdmin) && scope.isValidate() === false && scope.$root.closedForms === false) {
            return 'lock_close_by_myself';
          }
          return 'lock_close';
        } else if (scope.isValidate()) {
          return 'lock_open_disabled';
        } else {
          return 'lock_open';
        }
      };
      scope.valide = function() {
        var data;
        if (scope.$root.closedForms === false) {
          if (scope.$parent.validNavigation().valid === false) {
            return messageFlash.displayError($filter('translateText')('SAVE_BEFORE_LOCK_OR_VALID'));
          } else {
            if (!scope.isValidate() || scope.isValidateByMyself() || scope.$root.currentPerson.isAdmin) {
              if (!scope.$root.verificationRequest || (scope.$root.verificationRequest.status === 'VERIFICATION_STATUS_CORRECTION' && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status === 'rejected')) {
                data = {};
                data.questionSetKey = scope.getTitleCode();
                data.periodCode = scope.$root.periodSelectedKey;
                data.scopeId = scope.$root.scopeSelectedId;
                data.lock = scope.isValidate() ? false : true;
                return downloadService.postJson('/awac/answer/validateQuestionSet', data, function(result) {
                  if (result.success) {
                    scope.$parent.validateQuestionSet(scope.getTitleCode(), data.lock);
                    return scope.$root.testCloseable();
                  }
                });
              }
            }
          }
        }
      };
      scope.isValidateByMyself = function() {
        if (scope.$parent.getQuestionSetValidator(scope.getTitleCode()) != null) {
          if (scope.$parent.getQuestionSetValidator(scope.getTitleCode()).identifier === scope.$root.currentPerson.identifier) {
            return true;
          }
        }
        return false;
      };
      scope.isValidate = function() {
        if (scope.$parent.getQuestionSetValidator(scope.getTitleCode()) != null) {
          return true;
        }
        return false;
      };
      scope.getValidator = function() {
        return scope.$parent.getQuestionSetValidator(scope.getTitleCode());
      };
      scope.getValidatorName = function() {
        if (scope.getValidator() != null) {
          scope.getValidator();
          return scope.getValidator().firstName + " " + scope.getValidator().lastName;
        }
      };
      scope.getLockerName = function() {
        if (scope.getLocker() != null) {
          return scope.getLocker().firstName + " " + scope.getLocker().lastName;
        }
      };
      scope.getValidateClass = function() {
        if (scope.isValidate()) {
          if (scope.$root.closedForms === false) {
            if (scope.isValidateByMyself() || scope.$root.currentPerson.isAdmin) {
              if (!scope.$root.verificationRequest || (scope.$root.verificationRequest.status === 'VERIFICATION_STATUS_CORRECTION' && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status === 'rejected')) {
                return 'validate_by_myself';
              }
            }
          }
          return 'validated';
        } else {
          return 'validate_no_valid';
        }
      };
      scope.verification = function(valid) {
        var comment, data;
        data = {
          questionSetKey: scope.getTitleCode(),
          periodKey: scope.$root.periodSelectedKey,
          scopeId: scope.$root.scopeSelectedId,
          verification: {
            status: 'approved'
          }
        };
        if (valid) {
          return downloadService.postJson('/awac/verification/verify', data, function(result) {
            if (result.success) {
              scope.$parent.verifyQuestionSet(scope.getTitleCode(), result.data);
              return scope.$emit('TEST_CLOSING_VALIDATION');
            }
          });
        } else {
          if (scope.$parent.getQuestionSetVerification(scope.getTitleCode()) != null) {
            comment = scope.$parent.getQuestionSetVerification(scope.getTitleCode()).comment;
          }
          data = {
            questionSetCode: scope.getTitleCode(),
            refreshVerificationStatus: scope.refreshVerificationStatus,
            comment: comment
          };
          return modalService.show(modalService.VERIFICATION_REJECT, data);
        }
      };
      scope.refreshVerificationStatus = function(status) {
        return scope.$parent.verifyQuestionSet(scope.getTitleCode(), status);
      };
      scope.getVerificationClass = function(valid, disabled) {
        if (disabled == null) {
          disabled = false;
        }
        if (disabled === true) {
          if ((scope.$parent.getQuestionSetVerification(scope.getTitleCode()) != null) && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status === 'approved') {
            return 'verified_valid';
          } else if ((scope.$parent.getQuestionSetVerification(scope.getTitleCode()) != null) && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status === 'rejected') {
            return 'verified_rejected';
          } else {
            return 'verified_unverified';
          }
        } else {
          if (valid) {
            if ((scope.$parent.getQuestionSetVerification(scope.getTitleCode()) != null) && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status === 'approved') {
              return 'verification_approved';
            } else {
              return 'verification_approval';
            }
          } else {
            if ((scope.$parent.getQuestionSetVerification(scope.getTitleCode()) != null) && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status === 'rejected') {
              return 'verification_rejected';
            } else {
              return 'verification_reject';
            }
          }
        }
      };
      scope.displayVerificationRejectedMessage = function() {
        var data;
        if ((scope.$parent.getQuestionSetVerification(scope.getTitleCode()) != null) && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status !== 'approved') {
          data = {
            readOnly: true,
            comment: scope.$parent.getQuestionSetVerification(scope.getTitleCode()).comment
          };
          return modalService.show(modalService.VERIFICATION_REJECT, data);
        }
      };
      scope.getVerifier = function() {
        if (scope.$parent.getQuestionSetVerification(scope.getTitleCode()) != null) {
          return scope.$parent.getQuestionSetVerification(scope.getTitleCode()).verifier;
        }
        return null;
      };
      scope.getVerifierName = function() {
        if (scope.$parent.getQuestionSetVerification(scope.getTitleCode()) != null) {
          return scope.$parent.getQuestionSetVerification(scope.getTitleCode()).verifier.firstName + " " + scope.$parent.getQuestionSetVerification(scope.getTitleCode()).verifier.lastName;
        }
      };
      return scope.isVerificationDisabled = function() {
        if ($location.path() === '/submit') {
          return true;
        }
        return false;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacSubSubTitle", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-sub-sub-title.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      return scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacBlock", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngCondition: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-block.html",
    replace: true,
    transclude: true,
    link: function(scope, element) {
      directiveService.autoScopeImpl(scope);
      return scope.$watch('ngCondition', function(n, o) {
        return scope.$root.$broadcast('CONDITION');
      });
    }
  };
});angular.module('app.directives').directive("mmAwacRepetitionName", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-repetition-name.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      return scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacDocumentQuestion", function(directiveService, translationService, $upload, messageFlash, modalService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-document-question.html",
    replace: true,
    compile: function() {
      return {
        pre: function(scope) {
          directiveService.autoScopeImpl(scope);
          scope.getDisabled = function() {
            return scope.$parent.isDisabled();
          };
          scope.getQuestionCode = function() {
            return scope.$parent.getQuestionCode();
          };
          scope.getAnswer = function() {
            return scope.$parent.getAnswer(scope.getDataToCompare());
          };
          if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
            scope.$watch('getAnswer().value', function(o, n) {
              if ("" + n !== "" + o) {
                return scope.$parent.edited();
              }
            });
          }
          scope.inDownload = false;
          scope.percent = 0;
          scope.$watch('percent', function() {
            var _ref;
            return scope.style = {
              "width": scope.percent + "%",
              "color": (_ref = scope.percent > 50) != null ? _ref : {
                "white": "black"
              }
            };
          });
          scope.getDisabled = function() {
            return scope.$parent.isDisabled();
          };
          scope.openDocumentManager = function() {
            var args;
            if (scope.getAnswer() !== null) {
              args = {};
              args.listDocuments = scope.getAnswer().value;
              args.readyOnly = scope.getDataToCompare() === true || scope.getIsAggregation() === true || scope.getDisabled() === true;
              args.wasEdited = function() {
                return scope.$parent.edited();
              };
              return modalService.show(modalService.DOCUMENT_MANAGER, args);
            }
          };
          scope.getFileNumber = function() {
            if (scope.getAnswer() === null || scope.getAnswer().value === null || scope.getAnswer().value === void 0) {
              return 0;
            }
            return Object.keys(scope.getAnswer().value).length;
          };
          return scope.onFileSelect = function($files) {
            var file, i;
            scope.inDownload = true;
            i = 0;
            while (i < $files.length) {
              file = $files[i];
              scope.upload = $upload.upload({
                url: "/awac/file/upload/",
                data: {
                  myObj: scope.myModelObj
                },
                file: file
              }).progress(function(evt) {
                scope.percent = parseInt(100.0 * evt.loaded / evt.total);
                return;
              }).success(function(data, status, headers, config) {
                var fileName;
                scope.percent = 0;
                scope.inDownload = false;
                fileName = data.name;
                messageFlash.displaySuccess("The file " + fileName + " was upload successfully");
                if (scope.getAnswer().value === null || scope.getAnswer().value === void 0) {
                  scope.getAnswer().value = {};
                }
                scope.getAnswer().value[data.id] = data.name;
                scope.$parent.edited();
                return;
              }).error(function(data, status, headers, config) {
                scope.percent = 0;
                scope.inDownload = false;
                messageFlash.displayError("The upload of the file was faild");
                return;
              });
              i++;
            }
            return;
          };
        }
      };
    }
  };
});angular.module('app.directives').directive("mmAwacRealQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-real-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getDisabled = function() {
        return scope.$parent.isDisabled();
      };
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
      return scope.setErrorMessage = function(errorMessage) {
        return scope.$parent.setErrorMessage(errorMessage);
      };
    }
  };
});angular.module('app.directives').directive("mmAwacIntegerQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-integer-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getDisabled = function() {
        return scope.$parent.isDisabled();
      };
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
      return scope.setErrorMessage = function(errorMessage) {
        return scope.$parent.setErrorMessage(errorMessage);
      };
    }
  };
});angular.module('app.directives').directive("mmAwacRepetitionQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionSetCode: '=',
      ngIteration: '=',
      ngRepetitionMap: '=',
      ngCondition: '=',
      ngTabSet: '=',
      ngTab: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-repetition-question.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      if (scope.getTabSet() != null) {
        scope.$parent.addTabSet(scope.getTabSet(), scope.getTab(), scope.getRepetitionMap());
      }
      scope.isQuestionLocked = function() {
        if ((scope.$parent.getQuestionSetLocker(scope.getQuestionSetCode()) != null) || (scope.$parent.getValidatorByQuestionCode(scope.getQuestionSetCode()) != null)) {
          return true;
        }
        return false;
      };
      scope.getQuestionSet = function() {
        return scope.$parent.getQuestionSet(scope.getQuestionSetCode());
      };
      scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
      scope.removeAnwser = function() {
        return scope.$parent.removeIteration(scope.getQuestionSetCode(), scope.getIteration(), scope.getRepetitionMap());
      };
      return scope.$watch('ngCondition', function() {
        return scope.$root.$broadcast('CONDITION');
      });
    }
  };
});angular.module('app.directives').directive("mmAwacRealWithUnitQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-real-with-unit-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getDisabled = function() {
        return scope.$parent.isDisabled();
      };
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
        scope.$watch('getAnswer().unitId', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
      scope.getUnits = function() {
        var unitCategory;
        unitCategory = scope.$parent.getUnitCategories();
        if (unitCategory === null) {
          return null;
        }
        return unitCategory.units;
      };
      return scope.setErrorMessage = function(errorMessage) {
        return scope.$parent.setErrorMessage(errorMessage);
      };
    }
  };
});angular.module('app.directives').directive("mmAwacRepetitionAddButton", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionSetCode: '=',
      ngIteration: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-repetition-add-button.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.isQuestionLocked = function() {
        if ((scope.$parent.getQuestionSetLocker(scope.getQuestionSetCode()) != null) || (scope.$parent.getValidatorByQuestionCode(scope.getQuestionSetCode()) != null)) {
          return true;
        }
        return false;
      };
      return scope.addIteration = function() {
        return scope.$parent.addIteration(scope.getQuestionSetCode(), scope.getIteration());
      };
    }
  };
});angular.module('app.directives').directive("mmAwacPercentageQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-percentage-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getDisabled = function() {
        return scope.$parent.isDisabled();
      };
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
      return scope.setErrorMessage = function(errorMessage) {
        return scope.$parent.setErrorMessage(errorMessage);
      };
    }
  };
});angular.module('app.directives').directive("mmAwacStringQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-string-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getDisabled = function() {
        return scope.$parent.isDisabled();
      };
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        return scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
    }
  };
});angular.module('app.directives').directive("mmAwacSelectQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-select-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getDisabled = function() {
        return scope.$parent.isDisabled();
      };
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
      return scope.getOptions = function() {
        var codeList;
        codeList = scope.$parent.getCodeList();
        if (codeList === null) {
          return null;
        }
        return codeList.codeLabels;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacBooleanQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-boolean-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.radioName = "name_" + (Math.floor(Math.random() * 1000000));
      scope.getDisabled = function() {
        return scope.$parent.isDisabled();
      };
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        return scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
    }
  };
});angular.module('app.directives').directive("mmPlusMinusToggleButton", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-plus-minus-toggle-button.html",
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      return scope.toggle = function() {
        if (scope.ngModel) {
          return scope.ngModel = false;
        } else {
          return scope.ngModel = true;
        }
      };
    }
  };
});angular.module('app.directives').directive("mmAwacResultLegend", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '=',
      ngMode: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-result-legend.html",
    replace: true,
    link: function(scope, element) {
      directiveService.autoScopeImpl(scope);
      return scope.getNumber = function(rl) {
        var index, l, result, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        if (!rl) {
          return void 0;
        }
        result = null;
        index = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          l = _ref[_i];
          if (l.leftScope1Value + l.leftScope2Value + l.leftScope3Value + l.leftOutOfScopeValue + l.rightScope1Value + l.rightScope2Value + l.rightScope3Value + l.rightOutOfScopeValue > 0) {
            index += 1;
            if (l.indicatorName === rl.indicatorName) {
              result = index;
              break;
            }
          }
        }
        return result;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacMunicipalityMenu", function() {
  return {
    restrict: "E",
    templateUrl: "$/angular/templates/mm-awac-municipality-menu.html",
    transclude: true,
    replace: true,
    link: function() {}
  };
});angular.module('app.directives').directive("mmAwacEnterpriseMenu", function() {
  return {
    restrict: "E",
    templateUrl: "$/angular/templates/mm-awac-enterprise-menu.html",
    transclude: true,
    replace: true,
    link: function() {}
  };
});angular.module('app.controllers').controller("OrganizationManagerCtrl", function($scope, translationService, modalService, downloadService, messageFlash) {
  $scope.isLoading = false;
  if ($scope.$root.periods != null) {
    $scope.assignPeriod = $scope.$root.periods[0].key;
  } else {
    $scope.$watch('$root.periods', function() {
      return $scope.assignPeriod = $scope.$root.periods[0].key;
    });
  }
  $scope.isPeriodChecked = {};
  $scope.selectedPeriodForEvent = $scope.$root.periods[0].key;
  $scope.allFieldValid = function() {
    return $scope.nameInfo.isValid && (($scope.nameInfo.field !== $scope.organization.name) || ($scope.statisticsAllowed !== $scope.organization.statisticsAllowed));
  };
  $scope.nameInfo = {
    fieldTitle: "ORGANIZATION_NAME",
    validationRegex: "^.{1,255}$",
    validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH",
    field: null,
    hideIsValidIcon: true,
    isValid: true,
    focus: function() {
      return true;
    }
  };
  $scope.statisticsAllowed = false;
  modalService.show(modalService.LOADING);
  return downloadService.getJson('awac/organization/getMyOrganization', function(result) {
    modalService.close(modalService.LOADING);
    if (result.success) {
      $scope.organization = result.data;
      $scope.nameInfo.field = $scope.organization.name;
      $scope.statisticsAllowed = $scope.organization.statisticsAllowed;
      downloadService.getJson('awac/organization/events/load', function(result) {
        if (result.success) {
          return $scope.events = result.data.organizationEventList;
        }
      });
      $scope.toForm = function() {
        if ($scope.$root.mySites.length > 0) {
          $scope.$root.scopeSelectedId = $scope.$root.mySites[0].id;
          $scope.$root.periodSelectedKey = $scope.$root.mySites[0].listPeriodAvailable[0].key;
          return $scope.$root.navToLastFormUsed();
        } else {
          $scope.$root.scopeSelectedId = void 0;
          $scope.$root.periodSelectedKey = void 0;
          return $scope.$root.nav('/noScope');
        }
      };
      $scope.editOrCreateEvent = function(event) {
        var params, period, _i, _len, _ref;
        params = {};
        params.organization = $scope.organization;
        params.events = $scope.events;
        _ref = $scope.$root.periods;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          period = _ref[_i];
          if (period.key === $scope.selectedPeriodForEvent) {
            params.period = period;
          }
        }
        if (event != null) {
          params.event = event;
        }
        return modalService.show(modalService.EDIT_EVENT, params);
      };
      return $scope.saveOrganization = function() {
        var data;
        if (!$scope.allFieldValid) {
          return false;
        }
        data = {
          name: $scope.nameInfo.field,
          statisticsAllowed: $scope.statisticsAllowed
        };
        $scope.isLoading = true;
        return downloadService.postJson('/awac/organization/update', data, function(result) {
          $scope.isLoading = false;
          if (result.success) {
            messageFlash.displaySuccess("CHANGES_SAVED");
            $scope.$root.organizationName = $scope.nameInfo.field;
          }
          return false;
        });
      };
    }
  });
});angular.module('app.controllers').controller("LoginCtrl", function($scope, downloadService, $location, $filter, messageFlash, $compile, $timeout, modalService, translationService) {
  $scope.loading = false;
  $scope.tabActive = [];
  $scope.$watch('tabActive[1]', function() {
    if ($scope.tabActive[1] !== true) {
      $scope.forgotEmailSuccessMessage = null;
      return $scope.forgotPasswordInfo.field = "";
    }
  });
  $scope.enterEvent = function() {
    if ($scope.tabActive[0] === true) {
      return $scope.send({
        anonymous: false
      });
    } else if ($scope.tabActive[1] === true) {
      return $scope.sendForgotPassword();
    }
  };
  $scope.loginInfo = {
    fieldTitle: "LOGIN_FORM_LOGIN_FIELD_TITLE",
    id: 'loginForm',
    inputName: 'identifier',
    fieldType: "text",
    placeholder: "LOGIN_FORM_LOGIN_FIELD_PLACEHOLDER",
    validationRegex: "^\\S{5,20}$",
    validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH",
    field: "",
    isValid: false,
    focus: function() {
      return $scope.tabActive[0];
    }
  };
  $scope.passwordInfo = {
    fieldTitle: "LOGIN_FORM_PASSWORD_FIELD_TITLE",
    inputName: 'password',
    fieldType: "password",
    validationRegex: "^\\S{5,20}$",
    validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
    field: "",
    isValid: false
  };
  $scope.forgotPasswordInfo = {
    fieldTitle: "IDENTIFIENT_OR_EMAIL",
    inputName: 'password',
    fieldType: "text",
    validationRegex: "^\\S+$",
    validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
    field: "",
    isValid: false,
    focus: function() {
      return $scope.tabActive[1];
    }
  };
  $scope.connectionFieldValid = function() {
    if ($scope.loginInfo.isValid && $scope.passwordInfo.isValid) {
      return true;
    }
    return false;
  };
  $scope.forgotPasswordFieldValid = function() {
    if ($scope.forgotPasswordInfo.isValid && $scope.forgotPasswordInfo.isValid) {
      return true;
    }
    return false;
  };
  $scope.send = function(options) {
    var dto;
    $scope.isLoading = true;
    if (options.anonymous) {
      dto = {
        login: null,
        password: null,
        interfaceName: $scope.$root.instanceName
      };
    } else {
      dto = {
        login: $scope.loginInfo.field,
        password: $scope.passwordInfo.field,
        interfaceName: $scope.$root.instanceName
      };
    }
    downloadService.postJson('/awac/login', dto, function(result) {
      var params;
      if (result.success) {
        $scope.$root.loginSuccess(result.data);
        messageFlash.displaySuccess(translationService.get('CONNECTION_MESSAGE_SUCCESS'));
        return $scope.isLoading = false;
      } else {
        $scope.isLoading = false;
        if (result.data.__type === 'eu.factorx.awac.dto.myrmex.get.MustChangePasswordExceptionsDTO') {
          params = {
            login: $scope.loginInfo.field,
            password: $scope.passwordInfo.field
          };
          return modalService.show(modalService.CONNECTION_PASSWORD_CHANGE, params);
        }
      }
    });
    return false;
  };
  $scope.forgotEmailSuccessMessage = null;
  $scope.sendForgotPassword = function() {
    $scope.isLoading = true;
    downloadService.postJson('/awac/forgotPassword', {
      identifier: $scope.forgotPasswordInfo.field,
      interfaceName: $scope.$root.instanceName
    }, function(result) {
      if (result.success) {
        $scope.forgotEmailSuccessMessage = translationService.get('LOGIN_FORGOT_PASSWORD_SUCCESS');
        $scope.isLoading = false;
        return;
      } else {
        $scope.isLoading = false;
        return;
      }
    });
    return false;
  };
  $scope.injectRegistrationDirective = function() {
    var directive, directiveName;
    console.log("$scope.injectRegistrationDirective");
    if ($scope.$root != null) {
      console.log($scope.$root.instanceName);
      if ($scope.$root.instanceName === 'enterprise') {
        directiveName = "mm-awac-registration-enterprise";
      } else if ($scope.$root.instanceName === 'municipality') {
        directiveName = "mm-awac-registration-municipality";
      }
      directive = $compile("<" + directiveName + "></" + directiveName + ">")($scope);
      return $('.inject-registration-form').append(directive);
    }
  };
  return $timeout(function() {
    return $scope.injectRegistrationDirective();
  }, 0);
});angular.module('app.controllers').controller("ResultsCtrl", function($scope, $window, $filter, downloadService, modalService, messageFlash, translationService) {
  $scope.displayFormMenu = true;
  $scope.verificationRequests = [];
  $scope.$root.$watch('mySites', function(nv) {
    var s, _i, _len, _ref, _results;
    console.log('watch $root.mySites');
    $scope.mySites = angular.copy($scope.$root.mySites);
    _ref = $scope.mySites;
    _results = [];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      s = _ref[_i];
      _results.push("" + s.id === "" + $scope.$root.scopeSelectedId ? s.selected = true : void 0);
    }
    return _results;
  }, true);
  $scope.getVerificationRequestStatus = function() {
    if ($scope.$root.verificationRequest != null) {
      return $scope.$root.verificationRequest.status;
    }
  };
  $scope.getVerificationRequestOrganizationVerifierName = function() {
    var _ref, _ref2;
    return (_ref = $scope.$root.verificationRequest) != null ? (_ref2 = _ref.organizationVerifier) != null ? _ref2.name : void 0 : void 0;
  };
  $scope.$watch('$root.periodToCompare', function() {
    return $scope.reload();
  });
  $scope.$watch('mySites', function() {
    console.log('watch mySites');
    return $scope.reload();
  }, true);
  downloadService.getJson('/awac/verification/verificationRequests/' + $scope.$root.periodSelectedKey, function(result) {
    if (!result.success) {
      return messageFlash.displayError(result.data.message);
    } else {
      return $scope.verificationRequests = result.data.list;
    }
  });
  $scope.downloadVerificationReport = function(verificationRequest) {
    var url;
    url = '/awac/file/download/' + verificationRequest.verificationSuccessFileId;
    return $window.open(url);
  };
  $scope.exportPdf = function() {
    var dto, sites;
    sites = $scope.mySites.filter(function(e) {
      return e.selected;
    });
    dto = {
      __type: 'eu.factorx.awac.dto.awac.post.GetReportParametersDTO',
      periodKey: $scope.$root.periodSelectedKey,
      scopesIds: sites.map(function(s) {
        return s.id;
      })
    };
    if ($scope.$root.periodToCompare !== 'default') {
      dto.comparedPeriodKey = $scope.$root.periodToCompare;
    }
    $scope.pdfLoading = true;
    return downloadService.postJson('/awac/result/getReportAsPdf', dto, function(result) {
      return $scope.pdfLoading = false;
    });
  };
  $scope.exportXls = function() {
    var dto, sites;
    sites = $scope.mySites.filter(function(e) {
      return e.selected;
    });
    dto = {
      __type: 'eu.factorx.awac.dto.awac.post.GetReportParametersDTO',
      periodKey: $scope.$root.periodSelectedKey,
      scopesIds: sites.map(function(s) {
        return s.id;
      })
    };
    if ($scope.$root.periodToCompare !== 'default') {
      dto.comparedPeriodKey = $scope.$root.periodToCompare;
    }
    $scope.xlsLoading = true;
    return downloadService.postJson('/awac/result/getReportAsXls', dto, function(result) {
      return $scope.xlsLoading = false;
    });
  };
  $scope.dataURItoBlob = function(dataURI) {
    var ab, bb, byteString, i, ia, mimeString;
    byteString = atob(dataURI.split(",")[1]);
    mimeString = dataURI.split(",")[0].split(":")[1].split(";")[0];
    ab = new ArrayBuffer(byteString.length);
    ia = new Uint8Array(ab);
    i = 0;
    while (i < byteString.length) {
      ia[i] = byteString.charCodeAt(i);
      i++;
    }
    bb = new BlobBuilder();
    bb.append(ab);
    return bb.getBlob(mimeString);
  };
  $scope.reload = function() {
    var dto, sites;
    sites = $scope.mySites.filter(function(e) {
      return e.selected;
    });
    if (sites.length > 0) {
      $scope.o = void 0;
      $scope.leftTotalEmissions = void 0;
      $scope.leftTotalScope1 = void 0;
      $scope.leftTotalScope2 = void 0;
      $scope.leftTotalScope3 = void 0;
      $scope.rightTotalEmissions = void 0;
      $scope.rightTotalScope1 = void 0;
      $scope.rightTotalScope2 = void 0;
      $scope.rightTotalScope3 = void 0;
      modalService.show(modalService.LOADING);
      dto = {
        __type: 'eu.factorx.awac.dto.awac.post.GetReportParametersDTO',
        periodKey: $scope.$root.periodSelectedKey,
        scopesIds: sites.map(function(s) {
          return s.id;
        })
      };
      if ($scope.$root.periodToCompare !== 'default') {
        dto.comparedPeriodKey = $scope.$root.periodToCompare;
      }
      return downloadService.postJson('/awac/result/getReport', dto, function(result) {
        var line, _i, _len, _ref, _results;
        modalService.close(modalService.LOADING);
        if (result.success) {
          $scope.o = result.data;
          if ($scope.$root.instanceName === 'municipality') {
            $scope.o.reportDTOs.R_1 = $scope.o.reportDTOs.RCo_1;
            $scope.o.reportDTOs.R_2 = $scope.o.reportDTOs.RCo_2;
            $scope.o.reportDTOs.R_3 = $scope.o.reportDTOs.RCo_3;
            $scope.o.reportDTOs.R_4 = $scope.o.reportDTOs.RCo_4;
            $scope.o.reportDTOs.R_5 = $scope.o.reportDTOs.RCo_5;
            $scope.o.leftSvgDonuts.R_1 = $scope.o.leftSvgDonuts.RCo_1;
            $scope.o.leftSvgDonuts.R_2 = $scope.o.leftSvgDonuts.RCo_2;
            $scope.o.leftSvgDonuts.R_3 = $scope.o.leftSvgDonuts.RCo_3;
            $scope.o.leftSvgDonuts.R_4 = $scope.o.leftSvgDonuts.RCo_4;
            $scope.o.leftSvgDonuts.R_5 = $scope.o.leftSvgDonuts.RCo_5;
            $scope.o.rightSvgDonuts.R_1 = $scope.o.rightSvgDonuts.RCo_1;
            $scope.o.rightSvgDonuts.R_2 = $scope.o.rightSvgDonuts.RCo_2;
            $scope.o.rightSvgDonuts.R_3 = $scope.o.rightSvgDonuts.RCo_3;
            $scope.o.rightSvgDonuts.R_4 = $scope.o.rightSvgDonuts.RCo_4;
            $scope.o.rightSvgDonuts.R_5 = $scope.o.rightSvgDonuts.RCo_5;
            $scope.o.svgWebs.R_1 = $scope.o.svgWebs.RCo_1;
            $scope.o.svgWebs.R_2 = $scope.o.svgWebs.RCo_2;
            $scope.o.svgWebs.R_3 = $scope.o.svgWebs.RCo_3;
            $scope.o.svgWebs.R_4 = $scope.o.svgWebs.RCo_4;
            $scope.o.svgWebs.R_5 = $scope.o.svgWebs.RCo_5;
            $scope.o.svgHistograms.R_1 = $scope.o.svgHistograms.RCo_1;
            $scope.o.svgHistograms.R_2 = $scope.o.svgHistograms.RCo_2;
            $scope.o.svgHistograms.R_3 = $scope.o.svgHistograms.RCo_3;
            $scope.o.svgHistograms.R_4 = $scope.o.svgHistograms.RCo_4;
            $scope.o.svgHistograms.R_5 = $scope.o.svgHistograms.RCo_5;
          }
          $scope.leftTotalEmissions = 0;
          $scope.leftTotalScope1 = 0;
          $scope.leftTotalScope2 = 0;
          $scope.leftTotalScope3 = 0;
          $scope.rightTotalEmissions = 0;
          $scope.rightTotalScope1 = 0;
          $scope.rightTotalScope2 = 0;
          $scope.rightTotalScope3 = 0;
          _ref = $scope.o.reportDTOs.R_1.reportLines;
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            line = _ref[_i];
            $scope.leftTotalScope1 += line.leftScope1Value;
            $scope.leftTotalScope2 += line.leftScope2Value;
            $scope.leftTotalScope3 += line.leftScope3Value;
            $scope.rightTotalScope1 += line.rightScope1Value;
            $scope.rightTotalScope2 += line.rightScope2Value;
            $scope.rightTotalScope3 += line.rightScope3Value;
            $scope.leftTotalEmissions += line.leftScope1Value;
            $scope.leftTotalEmissions += line.leftScope2Value;
            $scope.leftTotalEmissions += line.leftScope3Value;
            $scope.rightTotalEmissions += line.rightScope1Value;
            $scope.rightTotalEmissions += line.rightScope2Value;
            _results.push($scope.rightTotalEmissions += line.rightScope3Value);
          }
          return _results;
        }
      });
    }
  };
  $scope.current_tab = 1;
  $scope.siteSelectionIsEmpty = function() {
    var filtered;
    filtered = $scope.mySites.filter(function(s) {
      return s.selected;
    });
    return filtered.length === 0;
  };
  $scope.downloadAsXls = function() {
    return $window.open('/awac/result/getReportAsXls/' + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId, translationService.get('RESULT_DOWNLOAD_START', null));
  };
  return $scope.downloadPdf = function() {};
});angular.module('app.controllers').controller("VerificationVerificationCtrl", function($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile) {
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  $scope.selectedRequest = null;
  modalService.show(modalService.LOADING);
  downloadService.getJson('/awac/verification/myVerificationRequests', function(result) {
    if (!result.success) {
      return modalService.close(modalService.LOADING);
    } else {
      modalService.close(modalService.LOADING);
      $scope.requests = result.data.list;
      return $scope.displayTable();
    }
  });
  $scope.displayTable = function() {
    if (!$scope.requests) {
      $scope.requests = [];
    }
    if ($scope.tableParams != null) {
      return $scope.tableParams.reload();
    } else {
      return $scope.tableParams = new ngTableParams({
        page: 1,
        count: 100
      }, {
        counts: [],
        total: 1,
        getData: function($defer, params) {
          var orderedData;
          orderedData = $filter("orderBy")($scope.requests, params.orderBy());
          $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
          params.total(orderedData.length);
          return;
        }
      });
    }
  };
  $scope.form = null;
  $scope.requestSelected = null;
  $scope.selectRequest = function(request) {
    var directiveName;
    $('.injectForm:first').empty();
    $('.injectFormMenu:first').empty();
    if ($scope.directiveMenu != null) {
      $scope.directiveMenu.remove();
    }
    if ($scope.directive != null) {
      $scope.directive.remove();
    }
    if (request != null) {
      $scope.requestSelected = request;
    }
    $scope.selectedRequest = $scope.requestSelected;
    $scope.$root.periodSelectedKey = $scope.requestSelected.period.key;
    $scope.$root.scopeSelectedId = $scope.requestSelected.scope.id;
    if (!$scope.form) {
      if ($scope.requestSelected.organizationCustomer.interfaceName === 'enterprise') {
        $scope.form = 'TAB2';
      }
      if ($scope.requestSelected.organizationCustomer.interfaceName === 'municipality') {
        $scope.form = 'TAB_C1';
      }
    }
    if ($scope.form === '/results') {
      directiveName = "<div ng-include=\"'$/angular/views/results.html'\" ng-controller=\"ResultsCtrl\"></div>";
    } else {
      directiveName = "<div ng-include=\"'$/angular/views/enterprise/" + $scope.form + ".html'\" ng-init=\"init('" + $scope.form + "')\" ng-controller=\"FormCtrl\"></div>";
    }
    $scope.directive = $compile(directiveName)($scope);
    $('.injectForm:first').append($scope.directive);
    $scope.directiveMenu = $compile("<mm-awac-" + $scope.requestSelected.organizationCustomer.interfaceName + "-menu form=\"" + $scope.form + "\"></mm-awac-" + $scope.requestSelected.organizationCustomer.interfaceName + "-menu>")($scope);
    $scope.displayMenu = true;
    $('.injectFormMenu:first').append($scope.directiveMenu);
    $scope.$root.mySites = [request.scope];
    return $scope.testClosingValidation();
  };
  $scope.isMenuCurrentlySelected = function(target) {
    if ('/form/' + $scope.form === target || $scope.form === target) {
      return true;
    }
    return false;
  };
  $scope.navTo = function(target) {
    var form;
    form = target.replace('/form/', '');
    $scope.form = form;
    return $scope.selectRequest();
  };
  $scope.consultEvent = function() {
    var data;
    data = {
      organizationCustomer: $scope.requestSelected.organizationCustomer
    };
    return modalService.show(modalService.CONSULT_EVENT, data);
  };
  $scope.$on('TEST_CLOSING_VALIDATION', function() {
    return $scope.testClosingValidation();
  });
  $scope.testClosingValidation = function() {
    return downloadService.getJson('/awac/answer/testClosingValidation/' + $scope.requestSelected.period.key + "/" + $scope.requestSelected.scope.id, function(result) {
      if (!result.success) {
        return modalService.close(modalService.LOADING);
      } else {
        return $scope.verificationFinalization = result.data;
      }
    });
  };
  $scope.removeRequest = function() {
    var i, request, _i, _len, _ref;
    i = 0;
    _ref = $scope.requests;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      request = _ref[_i];
      if (request.id === $scope.requestSelected.id) {
        $scope.requests.splice(i, 1);
      }
      i++;
    }
    $scope.displayTable();
    $('.injectForm:first').empty();
    $('.injectFormMenu:first').empty();
    return $scope.requestSelected = null;
  };
  return $scope.finalizeVerification = function() {
    var data;
    console.log($scope.verificationFinalization);
    data = {
      removeRequest: $scope.removeRequest,
      verificationSuccess: $scope.verificationFinalization.success,
      request: $scope.requestSelected
    };
    return modalService.show(modalService.VERIFICATION_FINALIZATION, data);
  };
});angular.module('app.controllers').controller("AdminDriverManageCtrl", function($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile) {
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  $scope.tempIdCounter = 0;
  $scope.drivers = null;
  modalService.show(modalService.LOADING);
  downloadService.getJson("/awac/admin/driver/all", function(result) {
    var driver, value, _i, _j, _len, _len2, _ref, _ref2;
    modalService.close(modalService.LOADING);
    if (result.success === true) {
      $scope.drivers = result.data.list;
      _ref = $scope.drivers;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        driver = _ref[_i];
        _ref2 = driver.driverValues;
        for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
          value = _ref2[_j];
          value.tempId = ++$scope.tempIdCounter;
        }
      }
      return $scope.orderDriverValues();
    }
  });
  $scope.addValue = function(driver) {
    driver.driverValues.push({
      tempId: ++$scope.tempIdCounter
    });
    return console.log($scope.tempIdCounter);
  };
  $scope.getPeriod = function(driver, currentDriverValue) {
    var driverValue, founded, period, periods, _i, _j, _len, _len2, _ref, _ref2;
    periods = [];
    _ref = $scope.$root.periods;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      period = _ref[_i];
      founded = false;
      _ref2 = driver.driverValues;
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        driverValue = _ref2[_j];
        if (period.key === (driverValue != null ? driverValue.fromPeriodKey : void 0) && period.key !== (currentDriverValue != null ? currentDriverValue.fromPeriodKey : void 0)) {
          founded = true;
          break;
        }
      }
      if (founded === false) {
        periods.push(period);
      }
    }
    return periods;
  };
  $scope.orderDriverValues = function() {
    var driver, driverValue, driverValues, founded, i, sortedDriver, _i, _j, _k, _len, _len2, _len3, _ref, _ref2, _results;
    _ref = $scope.drivers;
    _results = [];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      driver = _ref[_i];
      driverValues = [];
      _ref2 = driver.driverValues;
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        driverValue = _ref2[_j];
        i = 0;
        founded = false;
        for (_k = 0, _len3 = driverValues.length; _k < _len3; _k++) {
          sortedDriver = driverValues[_k];
          if (parseFloat(sortedDriver.fromPeriodKey) > parseFloat(driverValue.fromPeriodKey)) {
            driverValues.splice(i, 0, driverValue);
            founded = true;
            break;
          }
          i++;
        }
        if (founded === false) {
          driverValues.push(driverValue);
        }
      }
      _results.push(driver.driverValues = driverValues);
    }
    return _results;
  };
  $scope.save = function() {
    modalService.show(modalService.LOADING);
    return downloadService.postJson('/awac/admin/driver/update', {
      list: $scope.drivers
    }, function(result) {
      return modalService.close(modalService.LOADING);
    });
  };
  return $scope.remove = function(driver, valueTempId) {
    var i, value, _i, _len, _ref, _results;
    i = 0;
    _ref = driver.driverValues;
    _results = [];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      value = _ref[_i];
      if (value.tempId === valueTempId) {
        driver.driverValues.splice(i, 1);
        break;
      }
      _results.push(i++);
    }
    return _results;
  };
});angular.module('app.controllers').controller("ActionsCtrl", function($scope, $window, displayFormMenu, modalService, downloadService, $filter, messageFlash, translationService) {
  $scope.displayFormMenu = displayFormMenu;
  $scope.actions = [];
  $scope.typeOptions = [];
  $scope.statusOptions = [];
  $scope.gwpUnits = [];
  $scope.loadActions = function() {
    return downloadService.getJson("awac/actions/load", function(result) {
      var codeLists;
      if (result.success) {
        $scope.actions = result.data.reducingActions;
        codeLists = result.data.codeLists;
        $scope.typeOptions = _.findWhere(codeLists, {
          code: 'REDUCING_ACTION_TYPE'
        }).codeLabels;
        $scope.statusOptions = _.findWhere(codeLists, {
          code: 'REDUCING_ACTION_STATUS'
        }).codeLabels;
        return $scope.gwpUnits = result.data.gwpUnits;
      }
    });
  };
  $scope.createAction = function() {
    var params;
    params = {
      typeOptions: $scope.typeOptions,
      statusOptions: $scope.statusOptions,
      gwpUnits: $scope.gwpUnits,
      cb: $scope.loadActions
    };
    return modalService.show(modalService.CREATE_REDUCTION_ACTION, params);
  };
  $scope.updateAction = function(action) {
    var params;
    params = {
      typeOptions: $scope.typeOptions,
      statusOptions: $scope.statusOptions,
      gwpUnits: $scope.gwpUnits,
      action: action
    };
    return modalService.show(modalService.CREATE_REDUCTION_ACTION, params);
  };
  $scope.markActionAsDone = function(action) {
    var data;
    $scope.isLoading = true;
    data = angular.copy(action);
    data.statusKey = "2";
    data.completionDate = new Date();
    return downloadService.postJson('/awac/actions/save', data, function(result) {
      $scope.isLoading = false;
      if (result.success) {
        angular.extend(action, result.data);
        return messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
      }
    });
  };
  $scope.deleteAction = function(action) {
    $scope.isLoading = true;
    return downloadService.postJson("awac/actions/delete", action, function(result) {
      var e, index, _ref, _results;
      $scope.isLoading = false;
      if (result.success) {
        messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
        _ref = $scope.actions;
        _results = [];
        for (index in _ref) {
          e = _ref[index];
          if (e.id === action.id) {
            $scope.actions.splice(index, 1);
            break;
          }
        }
        return _results;
      }
    });
  };
  $scope.confirmDelete = function(action) {
    var params;
    params = {
      titleKey: "REDUCING_ACTION_DELETE_CONFIRMATION_TITLE",
      messageKey: "REDUCING_ACTION_DELETE_CONFIRMATION_MESSAGE",
      onConfirm: function() {
        return $scope.deleteAction(action);
      }
    };
    return modalService.show(modalService.CONFIRM_DIALOG, params);
  };
  $scope.exportActionsToXls = function() {
    return $window.open('/awac/actions/exportToXls', translationService.get('RESULT_DOWNLOAD_START', null));
  };
  $scope.getScopeName = function(scopeTypeKey, scopeId) {
    if (scopeTypeKey === "1") {
      return $scope.$root.organizationName;
    } else {
      return $scope.$root.mySites.filter(function(e) {
        return ("" + e.id) === ("" + scopeId);
      })[0].name;
    }
  };
  $scope.getTypeLabel = function(typeKey) {
    return _.findWhere($scope.typeOptions, {
      key: typeKey
    }).label;
  };
  $scope.getStatusLabel = function(statusKey) {
    return _.findWhere($scope.statusOptions, {
      key: statusKey
    }).label;
  };
  $scope.getGwpUnitSymbol = function(gwpUnitCodeKey) {
    if (gwpUnitCodeKey !== null) {
      return _.findWhere($scope.gwpUnits, {
        code: gwpUnitCodeKey
      }).name;
    }
  };
  return $scope.$watch('$root.mySites', function(n, o) {
    if (!!n) {
      return $scope.loadActions();
    }
  });
});angular.module('app').run(function(loggerService) {
  var log;
  loggerService.initialize();
  $('body').keydown(function(evt) {
    var loggerElement, state;
    if (evt.which === 32 && evt.altKey && evt.ctrlKey) {
      loggerElement = $('#logger');
      state = parseInt(loggerElement.attr('data-state'));
      return loggerElement.attr('data-state', (state + 1) % 3);
    }
  });
  log = loggerService.get('initializer');
  return log.info("Application is started");
});
angular.module('app.controllers').controller("MainCtrl", function($scope, downloadService, translationService, $sce, $location, $route, $routeParams, modalService, $timeout, messageFlash, $compile, $element, $window) {
  var directive, p;
  $scope.displayMenu = false;
  $scope.displayLittleMenu = false;
  $scope.isLoading = function() {
    var k;
    for (k in $scope.initialLoad) {
      if (!$scope.initialLoad[k]) {
        return true;
      }
    }
    return false;
  };
  $scope.initialLoad = {
    translations: false
  };
  if ($scope.$root.instanceName === "municipality" || $scope.$root.instanceName === "enterprise") {
    directive = $compile("<mm-awac-" + $scope.$root.instanceName + "-menu></mm-awac-" + $scope.$root.instanceName + "_menu>")($scope);
    p = $('.inject-menu:first', $element);
    $(directive).insertAfter(p);
  }
  $scope.missedTranslationLoadings = 0;
  $scope.$on("LOAD_FINISHED", function(event, args) {
    if (args.type === "TRANSLATIONS") {
      if (!args.success && $scope.missedTranslationLoadings < 3) {
        $scope.missedTranslationLoadings += 1;
        translationService.initialize($rootScope.language);
      } else {
        if (args.success) {
          $scope.missedTranslationLoadings = 0;
        }
        $scope.initialLoad.translations = args.success;
      }
    }
    return;
  });
  $scope.isMenuCurrentlySelected = function(loc) {
    if ($location.path().substring(0, loc.length) === loc) {
      return true;
    } else {
      return false;
    }
  };
  window.onbeforeunload = function(event) {
    var canBeContinue, result, _base;
    canBeContinue = true;
    if (($scope.getMainScope() != null) && (typeof $scope.getMainScope === "function" ? $scope.getMainScope().validNavigation : void 0) !== void 0) {
      result = typeof $scope.getMainScope === "function" ? typeof (_base = $scope.getMainScope()).validNavigation === "function" ? _base.validNavigation() : void 0 : void 0;
      if (result.valid === false) {
        return translationService.get('MODAL_CONFIRMATION_EXIT_FORM_MESSAGE');
      }
    }
  };
  $scope.$on('$routeChangeSuccess', function(event, args) {
    return $timeout(function() {
      return $scope.computeDisplayMenu();
    }, 0);
  });
  $scope.computeDisplayMenu = function() {
    if (($scope.getMainScope() != null) && ($scope.getMainScope().displayFormMenu != null) && $scope.getMainScope().displayFormMenu === true) {
      $scope.displayMenu = true;
    } else {
      $scope.displayMenu = false;
    }
    if (($scope.getMainScope() != null) && ($scope.getMainScope().displayLittleFormMenu != null) && $scope.getMainScope().displayLittleFormMenu === true) {
      return $scope.displayLittleMenu = true;
    } else {
      return $scope.displayLittleMenu = false;
    }
  };
  $scope.periodKey = null;
  $scope.$watch('$root.scopeSelectedId', function(o, n) {
    if (o !== n) {
      return $scope.computeScopeAndPeriod();
    }
  });
  $scope.$watch('$root.periodSelectedKey', function(o, n) {
    if (o !== n) {
      return $scope.computeScopeAndPeriod();
    }
  });
  $scope.computeScopeAndPeriod = function() {
    var k, url, v;
    console.log('----->>>' + $scope.$root.periodSelectedKey + "-" + $scope.$root.scopeSelectedId);
    if (($scope.$root.periodSelectedKey != null) && ($scope.$root.scopeSelectedId != null)) {
      $routeParams.form = $route.current.params.form;
      if ($route.current) {
        p = $route.current.$$route.originalPath;
        if (p === "/noScope") {
          url = $scope.$root.getDefaultRoute();
          $scope.$root.nav(url);
        } else {
          p = p.replace(new RegExp("\\/:period\\b", 'g'), '');
          p = p.replace(new RegExp("\\/:scope\\b", 'g'), '');
          for (k in $routeParams) {
            v = $routeParams[k];
            p = p.replace(new RegExp("\\:" + k + "\\b", 'g'), v);
          }
          if (p !== $route.current.$$route.originalPath) {
            $scope.$root.nav(p);
          }
        }
      }
      $scope.$root.computeAvailablePeriod();
      $scope.loadPeriodForComparison();
      $scope.loadFormProgress();
      return $scope.$root.testCloseable();
    }
  };
  $scope.periodsForComparison = [
    {
      'key': 'default',
      'label': translationService.get('NO_PERIOD_SELECTED')
    }
  ];
  $scope.$root.periodToCompare = 'default';
  $scope.save = function() {
    $scope.$broadcast('SAVE');
    return $scope.$root.$broadcast("REFRESH_LAST_SAVE_TIME");
  };
  $scope.getMainScope = function() {
    var mainScope;
    return mainScope = angular.element($('[ng-view]')[0]).scope();
  };
  $scope.loadPeriodForComparison = function() {
    var url;
    if (!!$scope.$root.scopeSelectedId) {
      url = '/awac/answer/getPeriodsForComparison/' + $scope.$root.scopeSelectedId;
      return downloadService.getJson(url, function(result) {
        var currentComparisonFounded, period, _i, _len, _ref;
        if (result.success) {
          $scope.periodsForComparison = [
            {
              'key': 'default',
              'label': translationService.get('NO_PERIOD_SELECTED')
            }
          ];
          currentComparisonFounded = false;
          _ref = result.data.periodDTOList;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            period = _ref[_i];
            if (period.key !== $scope.$root.periodSelectedKey) {
              $scope.periodsForComparison.push(period);
              if (period.key === $scope.$root.periodToCompare) {
                currentComparisonFounded = true;
              }
            }
          }
          if (currentComparisonFounded === false || $scope.$root.periodSelectedKey === $scope.$root.periodToCompare) {
            return $scope.$root.periodToCompare = 'default';
          }
        }
      });
    }
  };
  $scope.getProgress = function(form) {
    var formProgress, _i, _len, _ref;
    if ($scope.formProgress !== null) {
      _ref = $scope.formProgress;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        formProgress = _ref[_i];
        if (formProgress.form === form) {
          return formProgress.percentage;
        }
      }
    }
    return 0;
  };
  $scope.formProgress = null;
  $scope.loadFormProgress = function() {
    if (!!$scope.$root.scopeSelectedId && !!$scope.$root.periodSelectedKey) {
      return downloadService.getJson("/awac/answer/formProgress/" + $scope.$root.periodSelectedKey + "/" + $scope.$root.scopeSelectedId, function(result) {
        if (result.success) {
          return $scope.formProgress = result.data.listFormProgress;
        }
      });
    }
  };
  $scope.$on("REFRESH_LAST_SAVE_TIME", function(event, args) {
    var date, minuteToAdd;
    if (args !== void 0) {
      if (args.time === null) {
        date = null;
      } else {
        date = new Date(args.time);
        minuteToAdd = new Date().getTimezoneOffset();
        date = new Date(date.getTime() - minuteToAdd * 60000);
      }
    } else {
      date = new Date();
    }
    return $scope.lastSaveTime = date;
  });
  $scope.getClassContent = function() {
    if ($scope.$root.hideHeader() === false) {
      if ($scope.getMainScope() != null) {
        if (($scope.getMainScope().displayFormMenu != null) && $scope.getMainScope().displayFormMenu === true) {
          return 'content-with-menu';
        } else if (($scope.getMainScope().displayLittleFormMenu != null) && $scope.getMainScope().displayLittleFormMenu === true) {
          return 'content-with-little-menu';
        } else {
          return 'content-without-menu';
        }
      }
    }
    return '';
  };
  $scope.requestVerification = function() {
    return modalService.show('REQUEST_VERIFICATION');
  };
  $scope.cancelVerification = function() {
    var data;
    data = {
      url: "/awac/verification/setStatus",
      desc: 'CANCEL_VERIFICATION_DESC',
      successMessage: 'CHANGES_SAVED',
      title: 'CANCEL_VERIFICATION_TITLE',
      data: {
        scopeId: $scope.$root.scopeSelectedId,
        periodKey: $scope.$root.periodSelectedKey,
        newStatus: 'VERIFICATION_STATUS_REJECTED'
      },
      afterSave: function() {
        $scope.$root.verificationRequest = null;
        return $scope.$root.$broadcast('CLEAN_VERIFICATION');
      }
    };
    return modalService.show(modalService.PASSWORD_CONFIRMATION, data);
  };
  $scope.confirmVerifier = function() {
    return modalService.show(modalService.VERIFICATION_CONFIRMATION);
  };
  $scope.consultVerificationFinalComment = function() {
    var data;
    if ($scope.$root.verificationRequest != null) {
      data = {
        comment: $scope.$root.verificationRequest.verificationRejectedComment
      };
      return modalService.show(modalService.VERIFICATION_FINALIZATION_VISUALIZATION, data);
    }
  };
  $scope.resubmitVerification = function() {
    var data;
    data = {
      url: "/awac/verification/setStatus",
      desc: "VERIFICATION_RESUBMIT_DESC",
      successMessage: "CHANGES_SAVED",
      title: "VERIFICATION_RESUBMIT_TITLE",
      data: {
        scopeId: $scope.$root.scopeSelectedId,
        periodKey: $scope.$root.periodSelectedKey,
        newStatus: 'VERIFICATION_STATUS_VERIFICATION'
      },
      afterSave: function() {
        var _ref;
        return (_ref = $scope.$root.verificationRequest) != null ? _ref.status = 'VERIFICATION_STATUS_REJECTED' : void 0;
      }
    };
    return modalService.show(modalService.PASSWORD_CONFIRMATION, data);
  };
  $scope.consultVerification = function() {
    return modalService.show(modalService.VERIFICATION_DOCUMENT);
  };
  return $scope.navTo = function(target) {
    return $scope.$root.nav(target);
  };
});
angular.module('app').run(function($rootScope, $location, downloadService, messageFlash, $timeout, translationService, tmhDynamicLocale, $routeParams, $route, modalService) {
  $rootScope.languages = [];
  $rootScope.closeableForms = false;
  $rootScope.closedForms = false;
  $rootScope.languages[0] = {
    value: 'fr',
    label: 'Français'
  };
  $rootScope.languages[1] = {
    value: 'en',
    label: 'English'
  };
  $rootScope.languages[2] = {
    value: 'nl',
    label: 'Neederlands'
  };
  translationService.initialize('fr');
  $rootScope.language = 'fr';
  $rootScope.$watch('language', function(lang) {
    translationService.initialize(lang);
    return tmhDynamicLocale.set(lang.toLowerCase());
  });
  $rootScope.getRegisterKey = function() {
    return $rootScope.key;
  };
  $rootScope.isLogin = function() {
    return $location.path().substring(0, 6) === "/login";
  };
  $rootScope.hideHeader = function() {
    return $location.path().substring(0, 6) === "/login" || $location.path().substring(0, 13) === "/registration" || $location.path().substring(0, 26) === "/verification_registration";
  };
  $rootScope.logout = function() {
    return downloadService.postJson('/awac/logout', null, function(result) {
      console.log('logout !! ');
      $rootScope.currentPerson = null;
      $rootScope.periodSelectedKey = null;
      $rootScope.scopeSelectedId = null;
      $rootScope.mySites = null;
      $rootScope.organizationName = null;
      return $location.path('/login');
    });
  };
  $rootScope.testForm = function(period, scope) {
    var periodToFind, site, _i, _j, _k, _len, _len2, _len3, _ref, _ref2, _ref3;
    if ($rootScope.mySites != null) {
      _ref = $rootScope.mySites;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        site = _ref[_i];
        if (parseFloat(site.id) === parseFloat(scope)) {
          if ($rootScope.instanceName === 'enterprise') {
            _ref2 = site.listPeriodAvailable;
            for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
              periodToFind = _ref2[_j];
              if (period + "" === periodToFind.key + "") {
                return true;
              }
            }
          } else {
            _ref3 = $rootScope.periods;
            for (_k = 0, _len3 = _ref3.length; _k < _len3; _k++) {
              periodToFind = _ref3[_k];
              if (period + "" === periodToFind.key + "") {
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  };
  $rootScope.loginSuccess = function(data, skipRedirect) {
    var periodSelectedKey, scope, scopeSelectedId, site, _i, _j, _len, _len2, _ref, _ref2;
    console.log("DATA connection : ");
    console.log(angular.copy(data));
    $rootScope.periods = data.availablePeriods;
    $rootScope.currentPerson = data.person;
    $rootScope.organizationName = data.organizationName;
    $rootScope.mySites = data.myScopes;
    if (!!data.defaultSiteId && !!data.defaultPeriod) {
      if ($rootScope.testForm(data.defaultSiteId, data.defaultPeriod) === true) {
        scopeSelectedId = data.defaultSiteId;
        periodSelectedKey = data.defaultPeriod;
      }
    } else if (data.defaultSiteId != null) {
      _ref = $rootScope.mySites;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        scope = _ref[_i];
        if (scope.id === data.defaultSiteId) {
          scopeSelectedId = data.defaultSiteId;
        }
      }
    }
    if (!(scopeSelectedId != null)) {
      if ($rootScope.mySites.length > 0) {
        scopeSelectedId = $rootScope.mySites[0].id;
      }
    }
    if ((scopeSelectedId != null) && !(periodSelectedKey != null)) {
      _ref2 = $rootScope.mySites;
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        site = _ref2[_j];
        if (site.id === scopeSelectedId) {
          if ($rootScope.instanceName === 'enterprise') {
            if ((site.listPeriodAvailable != null) && site.listPeriodAvailable.length > 0) {
              periodSelectedKey = site.listPeriodAvailable[0].key;
            }
          } else {
            periodSelectedKey = $rootScope.periods[0].key;
          }
        }
      }
    }
    $rootScope.scopeSelectedId = scopeSelectedId;
    $rootScope.periodSelectedKey = periodSelectedKey;
    if ($rootScope.scopeSelectedId != null) {
      $rootScope.computeAvailablePeriod($rootScope.mySites[0].scope);
    }
    if (!skipRedirect) {
      return $rootScope.toDefaultForm();
    }
  };
  $rootScope.toDefaultForm = function() {
    console.log("to default form :" + $rootScope.getDefaultRoute());
    return $rootScope.nav($rootScope.getDefaultRoute());
  };
  $rootScope.$watch("mySites", function() {
    return $rootScope.computeAvailablePeriod();
  });
  $rootScope.computeAvailablePeriod = function(scopeId) {
    var currentPeriodFounded, period, site, _i, _j, _len, _len2, _ref, _ref2;
    if ($rootScope.instanceName === 'enterprise') {
      if (!(scopeId != null)) {
        scopeId = $rootScope.scopeSelectedId;
      }
      if (scopeId != null) {
        _ref = $rootScope.mySites;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          site = _ref[_i];
          if (site.scope === scopeId) {
            $rootScope.availablePeriods = site.listPeriodAvailable;
          }
        }
        currentPeriodFounded = false;
        if ($rootScope.availablePeriods != null) {
          _ref2 = $rootScope.availablePeriods;
          for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
            period = _ref2[_j];
            if (period.key === $rootScope.periodSelectedKey) {
              currentPeriodFounded = true;
            }
          }
          if (!currentPeriodFounded) {
            if ($rootScope.availablePeriods.length > 0) {
              return $rootScope.periodSelectedKey = $rootScope.availablePeriods[0].key;
            }
          }
        } else {
          $rootScope.availablePeriods = null;
          return $location.path("noScope");
        }
      }
    } else if ($rootScope.instanceName === 'municipality') {
      return $rootScope.availablePeriods = $rootScope.periods;
    }
  };
  $rootScope.getUserByIdentifier = function(identifier) {
    var user, _i, _len, _ref;
    _ref = $rootScope.users;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      user = _ref[_i];
      if (user.identifier === identifier) {
        return user;
      }
    }
    return null;
  };
  $rootScope.refreshUserData = function() {
    return downloadService.getJson('/awac/user/profile', function(result) {
      if (result.success) {
        return $rootScope.currentPerson = result.data;
      }
    });
  };
  $rootScope.refreshNotifications = function() {
    downloadService.getJson('/awac/notifications/get_notifications', function(result) {
      var n, _i, _len, _ref, _results;
      if (result.success) {
        _ref = result.data.notifications;
        _results = [];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          n = _ref[_i];
          _results.push(messageFlash.display(n.kind.toLowerCase(), n.messageFr, {
            hideAfter: 3600
          }));
        }
        return _results;
      }
    });
    return $timeout($rootScope.refreshNotifications, 3600 * 1000);
  };
  $rootScope.refreshNotifications();
  $rootScope.$on("$routeChangeSuccess", function(event, current, previous) {
    if ($routeParams.period != null) {
      $rootScope.periodSelectedKey = $routeParams.period;
    }
    if ($routeParams.scope != null) {
      return $rootScope.scopeSelectedId = parseInt($routeParams.scope);
    }
  });
  $rootScope.navToLastFormUsed = function() {
    if ($rootScope.getFormPath != null) {
      return $rootScope.nav($rootScope.getFormPath());
    } else {
      return $rootScope.nav($rootScope.getDefaultRoute());
    }
  };
  $rootScope.nav = function(loc, confirmed) {
    var canBeContinue, params, result, route, routeWithScopeAndPeriod, _base, _i, _len, _ref;
    if (confirmed == null) {
      confirmed = false;
    }
    console.log("NAV : " + loc);
    canBeContinue = true;
    if (($rootScope.getMainScope() != null) && $rootScope.getMainScope().validNavigation !== void 0 && confirmed === false) {
      result = typeof $rootScope.getMainScope === "function" ? typeof (_base = $rootScope.getMainScope()).validNavigation === "function" ? _base.validNavigation() : void 0 : void 0;
      if (result.valid === false) {
        canBeContinue = false;
        params = {};
        params.loc = loc;
        modalService.show(result.modalForConfirm, params);
      }
    }
    if (canBeContinue) {
      if ((((_ref = $rootScope.getMainScope()) != null ? _ref.formIdentifier : void 0) != null) && ($rootScope.currentPerson != null)) {
        downloadService.getJson("/awac/answer/unlockForm/" + $rootScope.getMainScope().formIdentifier + "/" + $rootScope.periodSelectedKey + "/" + $rootScope.scopeSelectedId, function(result) {});
      }
      routeWithScopeAndPeriod = ['/form', '/results', '/actions'];
      for (_i = 0, _len = routeWithScopeAndPeriod.length; _i < _len; _i++) {
        route = routeWithScopeAndPeriod[_i];
        if (loc.substring(0, route.length) === route) {
          $location.path(loc + "/" + $rootScope.periodSelectedKey + "/" + $rootScope.scopeSelectedId);
          return;
        }
      }
      $location.path(loc);
      return;
    }
  };
  $rootScope.getMainScope = function() {
    var mainScope;
    return mainScope = angular.element($('[ng-view]')[0]).scope();
  };
  $rootScope.testCloseable = function() {
    if ($rootScope.instanceName !== 'verification') {
      if (!!$rootScope.periodSelectedKey && !!$rootScope.scopeSelectedId) {
        return downloadService.getJson("/awac/answer/testClosing/" + $rootScope.periodSelectedKey + "/" + $rootScope.scopeSelectedId, function(result) {
          if (result.success) {
            $rootScope.closeableForms = result.data.closeable;
            $rootScope.closedForms = result.data.closed;
            return $rootScope.verificationRequest = result.data.verificationRequest;
          }
        });
      }
    }
  };
  $rootScope.closeForms = function() {
    if (($rootScope.periodSelectedKey != null) && ($rootScope.scopeSelectedId != null)) {
      return modalService.show(modalService.CONFIRM_CLOSING);
    }
  };
  $rootScope.showHelp = function() {
    if ($route.current.locals.helpPage != null) {
      return modalService.show(modalService.HELP, {
        template: $route.current.locals.helpPage
      });
    }
  };
  $rootScope.showConfidentiality = function() {
    return modalService.show(modalService.HELP, {
      template: 'confidentiality'
    });
  };
  return $rootScope.getVerificationRequestStatus = function() {
    var _ref;
    return (_ref = $rootScope.verificationRequest) != null ? _ref.status : void 0;
  };
});angular.module('app.controllers').controller("FormCtrl", function($scope, downloadService, messageFlash, translationService, modalService, $route, $timeout, $filter) {
  $scope.displayFormMenu = true;
  $scope.init = function(route) {
    $scope.formIdentifier = route;
    $scope.tabSet = {};
    $scope.dataToCompare = null;
    $scope.answerList = [];
    $scope.mapRepetition = {};
    $scope.mapQuestionSet = {};
    /*
    illustration of the structures
    $scope.mapRepetition['A15'] = [{'A15':1},
                                    {'A15':2}]

    $scope.mapRepetition['A16'] = [{'A16':1,'A15':1},
                                   {'A16':2,'A15':1}]
    */
    /*
    list of the questionSet key locked with locker like personDTO
    */
    $scope.datalocker = {};
    $scope.dataValidator = {};
    $scope.dataVerification = {};
    $scope.$on('CLEAN_VERIFICATION', function() {
      return $scope.dataVerification = [];
    });
    $scope.loading = true;
    $scope.errorMessage = null;
    modalService.show(modalService.LOADING);
    downloadService.getJson("/awac/answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$root.periodSelectedKey + "/" + $scope.$root.scopeSelectedId, function(result) {
      var answerSave, args, qSet, questionSetDTO, _i, _j, _len, _len2, _ref, _ref2;
      if (!result.success) {
        if (!!result.data.messageToTranslate) {
          $scope.errorMessage = $filter('translateText')(result.data.messageToTranslate);
        } else {
          $scope.errorMessage = result.data.message;
        }
        return modalService.close(modalService.LOADING);
      } else {
        console.log("loading form result : ");
        console.log(result.data);
        $scope.o = angular.copy(result.data);
        if ($scope.o.answersSave !== null && $scope.o.answersSave !== void 0) {
          answerSave = $scope.o.answersSave;
          $scope.answerList = answerSave.listAnswers;
        }
        $scope.loopRepetition = function(questionSetDTO, listQuestionSetRepetition, parent) {
          var answer, child, questionSetElement, _i, _j, _len, _len2, _ref, _ref2, _results;
          if (listQuestionSetRepetition == null) {
            listQuestionSetRepetition = [];
          }
          if (questionSetDTO.datalocker != null) {
            $scope.datalocker[questionSetDTO.code] = questionSetDTO.datalocker;
          }
          if (questionSetDTO.dataValidator != null) {
            $scope.dataValidator[questionSetDTO.code] = questionSetDTO.dataValidator;
          }
          if (questionSetDTO.verification != null) {
            $scope.dataVerification[questionSetDTO.code] = questionSetDTO.verification;
          }
          questionSetElement = {
            questionSetDTO: questionSetDTO,
            parent: parent
          };
          $scope.mapQuestionSet[questionSetDTO.code] = questionSetElement;
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
              _results.push($scope.loopRepetition(child, angular.copy(listQuestionSetRepetition), questionSetElement));
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
        if ($scope.$root != null) {
          $scope.$root.$broadcast("REFRESH_LAST_SAVE_TIME", args);
        }
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
          var answer, _i, _len, _ref;
          _ref = $scope.answerList;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            answer = _ref[_i];
            $scope.createTabWatcher(answer);
          }
          return $timeout(function() {
            var answer, _i, _len, _ref;
            modalService.close(modalService.LOADING);
            $scope.loading = false;
            _ref = $scope.answerList;
            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              answer = _ref[_i];
              if (answer.hasValidCondition === false && answer.value !== null) {
                answer.value = null;
              }
            }
            return $scope.$root.$broadcast('FORM_LOADING_FINISH');
          }, 0);
        }, 0);
        return;
      }
    });
    $scope.$on('SAVE_AND_NAV', function(event, args) {
      return $scope.save(args);
    });
    $scope.$on('SAVE', function() {
      return $scope.save();
    });
    $scope.save = function(argToNav) {
      var answer, finalList, listAnswerToSave, _i, _j, _len, _len2, _ref;
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
      finalList = [];
      for (_j = 0, _len2 = listAnswerToSave.length; _j < _len2; _j++) {
        answer = listAnswerToSave[_j];
        if (!$scope.isQuestionLocked(answer.questionKey) && !$scope.isQuestionValidate(answer.questionKey)) {
          finalList.push(answer);
        }
      }
      console.log("listAnswerToSave");
      console.log(finalList);
      if (finalList.length === 0) {
        messageFlash.displayInfo(translationService.get('ALL_ANSWERS_ALREADY_SAVED'));
        return modalService.close(modalService.LOADING);
      } else {
        $scope.o.answersSave.listAnswers = finalList;
        return downloadService.postJson('/awac/answer/save', $scope.o.answersSave, function(result) {
          var answer, founded, i, it, j, key, listToRemove, repetition, repetitionToRemove, _i, _j, _k, _len, _len2, _len3, _ref, _ref2;
          if (result.success) {
            listToRemove = [];
            _ref = Object.keys($scope.mapRepetition);
            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              key = _ref[_i];
              if (key !== '$$hashKey') {
                i = 0;
                while (i < $scope.mapRepetition[key].length) {
                  repetition = $scope.mapRepetition[key][i];
                  founded = false;
                  _ref2 = $scope.answerList;
                  for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
                    answer = _ref2[_j];
                    if ((answer.value != null) && (!(answer.isAggregation != null) || answer.isAggregation !== true) && $scope.compareRepetitionMap(answer.mapRepetition, repetition)) {
                      founded = true;
                      break;
                    }
                  }
                  if (founded === false) {
                    it = listToRemove.length;
                    listToRemove[it] = {};
                    listToRemove[it].code = key;
                    listToRemove[it].iteration = {};
                    listToRemove[it].iteration[key] = $scope.mapRepetition[key][i][key];
                    j = angular.copy($scope.mapRepetition[key][i]);
                    delete j[key];
                    listToRemove[it].map = j;
                  }
                  i++;
                }
              }
            }
            for (_k = 0, _len3 = listToRemove.length; _k < _len3; _k++) {
              repetitionToRemove = listToRemove[_k];
              $scope.removeIteration(repetitionToRemove.code, repetitionToRemove.iteration, repetitionToRemove.map);
            }
            i = $scope.answerList.length - 1;
            while (i >= 0) {
              answer = $scope.answerList[i];
              if (answer.wasEdited === true) {
                answer.wasEdited = false;
              }
              if (answer.toRemove === true) {
                $scope.answerList.splice(i, 1);
              }
              i--;
            }
            $scope.saveFormProgress();
            messageFlash.displaySuccess(translationService.get('ANSWERS_SAVED'));
            modalService.close(modalService.LOADING);
            if (argToNav !== null) {
              $scope.$root.nav(argToNav.loc, argToNav.confirmed);
            }
            return;
          } else {
            messageFlash.displayError(translationService.get('ERROR_THROWN_ON_SAVE') + data.message);
            modalService.close(modalService.LOADING);
            return;
          }
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
    $scope.getAnswerOrCreate = function(code, mapIteration, tabSet, tab, optional) {
      var answerLine, defaultUnitCode, question, result, value, wasEdited;
      if (tabSet == null) {
        tabSet = null;
      }
      if (tab == null) {
        tab = null;
      }
      if (optional == null) {
        optional = false;
      }
      if (code === null || code === void 0) {
        console.log("ERROR !! getAnswerOrCreate : code is null or undefined");
        return null;
      }
      result = $scope.getAnswer(code, mapIteration);
      if (result) {
        if (result.toRemove != null) {
          result.toRemove = false;
        }
        if ((tabSet != null) && !(result.tabSet != null)) {
          result.tabSet = tabSet;
          result.tab = tab;
        }
        result.optional = optional;
        return result;
      } else {
        value = null;
        defaultUnitCode = null;
        wasEdited = false;
        if ($scope.getQuestion(code) !== null) {
          question = $scope.getQuestion(code);
          if (question.defaultValue !== null) {
            value = question.defaultValue;
            wasEdited = true;
          }
          if (question.unitCategoryId != null) {
            if (question.defaultUnit != null) {
              defaultUnitCode = question.defaultUnit.code;
            } else {
              defaultUnitCode = $scope.getUnitCategories(code).mainUnitCode;
            }
          }
        }
        answerLine = {
          'questionKey': code,
          'value': value,
          'unitCode': defaultUnitCode,
          'mapRepetition': mapIteration,
          'lastUpdateUser': $scope.$root.currentPerson,
          'wasEdited': wasEdited
        };
        if (tabSet != null) {
          answerLine.tabSet = tabSet;
          answerLine.tab = tab;
        }
        answerLine.optional = optional;
        $scope.createTabWatcher(answerLine);
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
            $scope.answerList[len].toRemove = true;
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
    $scope.$watch('$root.periodToCompare', function() {
      if ($scope.$parent !== null && ($scope.$root.periodToCompare != null) && $scope.$root.periodToCompare !== 'default') {
        return downloadService.getJson('/awac/answer/getByForm/' + $scope.formIdentifier + "/" + $scope.$root.periodToCompare + "/" + $scope.$root.scopeSelectedId, function(result) {
          if (result.success) {
            return $scope.dataToCompare = result.data;
          } else {
            return $scope.dataToCompare = null;
          }
        });
      } else {
        return $scope.dataToCompare = null;
      }
    });
    $scope.saveFormProgress = function() {
      var answer, answered, formProgress, formProgressDTO, founded, key, listTotal, percentage, tabSet, total, _i, _j, _k, _l, _len, _len2, _len3, _len4, _len5, _len6, _m, _n, _ref, _ref2, _ref3, _ref4, _ref5, _ref6;
      percentage = 0;
      total = 0;
      answered = 0;
      listTotal = [];
      _ref = $scope.answerList;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        answer = _ref[_i];
        if (!(answer.tabSet != null)) {
          if (answer.optional !== true && answer.isAggregation !== true) {
            if (answer.hasValidCondition === void 0 || answer.hasValidCondition === null || answer.hasValidCondition === true) {
              total++;
              listTotal.push(answer);
              if (answer.value !== null) {
                answered++;
              }
            }
          }
        }
      }
      _ref2 = Object.keys($scope.tabSet);
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        key = _ref2[_j];
        if (key !== '$$hashKey') {
          _ref3 = $scope.tabSet[key];
          for (_k = 0, _len3 = _ref3.length; _k < _len3; _k++) {
            tabSet = _ref3[_k];
            if (tabSet.master != null) {
              _ref4 = $scope.answerList;
              for (_l = 0, _len4 = _ref4.length; _l < _len4; _l++) {
                answer = _ref4[_l];
                if ((answer.tabSet != null) && parseFloat(answer.tabSet) === parseFloat(key) && parseFloat(answer.tab) === parseFloat(tabSet.master)) {
                  if (answer.optional !== true && answer.isAggregation !== true) {
                    if (answer.hasValidCondition === void 0 || answer.hasValidCondition === null || answer.hasValidCondition === true) {
                      total++;
                      listTotal.push(answer);
                      if (answer.value !== null) {
                        answered++;
                      }
                    }
                  }
                }
              }
            } else {
              _ref5 = $scope.answerList;
              for (_m = 0, _len5 = _ref5.length; _m < _len5; _m++) {
                answer = _ref5[_m];
                if ((answer.tabSet != null) && parseFloat(answer.tabSet) === parseFloat(key) && parseFloat(answer.tab) === 1) {
                  if (answer.optional !== true && answer.isAggregation !== true) {
                    if (answer.hasValidCondition === void 0 || answer.hasValidCondition === null || answer.hasValidCondition === true) {
                      total++;
                      listTotal.push(answer);
                      if (answer.value !== null) {
                        answered++;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      if (answered === 0) {
        percentage = 0;
      } else {
        percentage = answered / total * 100;
        percentage = Math.floor(percentage);
      }
      console.log("PROGRESS : " + answered + "/" + total + "=" + percentage);
      console.log(listTotal);
      formProgressDTO = {};
      formProgressDTO.form = $scope.formIdentifier;
      formProgressDTO.period = $scope.$root.periodSelectedKey;
      formProgressDTO.scope = $scope.$root.scopeSelectedId;
      formProgressDTO.percentage = percentage;
      founded = false;
      if ($scope.$parent.formProgress != null) {
        _ref6 = $scope.$parent.formProgress;
        for (_n = 0, _len6 = _ref6.length; _n < _len6; _n++) {
          formProgress = _ref6[_n];
          if (formProgress.form === $scope.formIdentifier) {
            founded = true;
            formProgress.percentage = percentage;
          }
        }
      } else {
        $scope.$parent.formProgress = [];
      }
      if (founded === false) {
        $scope.$parent.formProgress[$scope.$parent.formProgress.length] = formProgressDTO;
      }
      return downloadService.postJson('/awac/answer/formProgress', formProgressDTO, function(result) {
        if (result.success) {
          return;
        }
      });
    };
    $scope.addTabSet = function(tabSet, tab, mapRepetition) {
      var i, ite, wasCreated, _ref;
      ite = null;
      wasCreated = false;
      if (!($scope.tabSet[tabSet] != null)) {
        $scope.tabSet[tabSet] = [];
        $scope.tabSet[tabSet][0] = {};
        $scope.tabSet[tabSet][0].mapRepetition = mapRepetition;
        ite = 0;
        wasCreated = true;
      } else {
        i = 0;
        while (i < $scope.tabSet[tabSet].length) {
          if ($scope.compareRepetitionMap(mapRepetition, $scope.tabSet[tabSet][i].mapRepetition)) {
            ite = i;
            break;
          }
          i++;
        }
        if (ite === null) {
          ite = $scope.tabSet[tabSet].length;
          $scope.tabSet[tabSet][ite] = {};
          $scope.tabSet[tabSet][ite].mapRepetition = mapRepetition;
        }
      }
      if (!($scope.tabSet[tabSet][ite][tab] != null)) {
        $scope.tabSet[tabSet][ite][tab] = {};
        $scope.tabSet[tabSet][ite][tab].active = (_ref = tab === 1) != null ? _ref : {
          "true": false
        };
        wasCreated = true;
      }
      if (!($scope.tabSet[tabSet][ite][tab].listToCompute != null)) {
        $scope.tabSet[tabSet][ite][tab].listToCompute = [];
      }
      return ite;
    };
    $scope.getStatusClassForTab = function(tabSet, tab, mapRepetition) {
      var allWasSave, answer, atLeastOneQuestion, i, isFinish, ite, _i, _len, _ref;
      isFinish = true;
      i = 0;
      while (i < $scope.tabSet[tabSet].length) {
        if ($scope.compareRepetitionMap(mapRepetition, $scope.tabSet[tabSet][i].mapRepetition)) {
          ite = i;
          break;
        }
        i++;
      }
      allWasSave = true;
      atLeastOneQuestion = false;
      _ref = $scope.tabSet[tabSet][ite][tab].listToCompute;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        answer = _ref[_i];
        if (!(answer.hasValidCondition != null) || answer.hasValidCondition === true && answer.optional !== true && answer.isAggregation !== true) {
          atLeastOneQuestion = true;
          if (answer.wasEdited !== void 0 && answer.wasEdited === true) {
            allWasSave = false;
          }
          if (answer.value === null) {
            isFinish = false;
          }
        }
      }
      if (isFinish === true && atLeastOneQuestion === false) {
        isFinish === false;
      }
      if (isFinish === true) {
        if (allWasSave === false) {
          return 'answer_temp';
        }
        return 'answer';
      } else {
        if (allWasSave === false) {
          return 'pending_temp';
        }
        return 'pending';
      }
    };
    $scope.createTabWatcher = function(answer) {
      var answerToTest, i, ite, j, tab, tabSet;
      if (answer.tabSet != null) {
        tabSet = answer.tabSet;
        tab = answer.tab;
        ite = $scope.addTabSet(tabSet, tab, answer.mapRepetition);
        j = $scope.tabSet[tabSet][ite][tab].listToCompute.length;
        j--;
        while (j >= 0) {
          answerToTest = $scope.tabSet[tabSet][ite][tab].listToCompute[j];
          if (answerToTest.questionKey === answer.questionKey && $scope.compareRepetitionMap(answer.mapRepetition, answerToTest.mapRepetition)) {
            $scope.tabSet[tabSet][ite][tab].listToCompute.splice(j, 1);
          }
          j--;
        }
        i = $scope.tabSet[tabSet][ite][tab].listToCompute.length;
        $scope.tabSet[tabSet][ite][tab].listToCompute[i] = answer;
        $scope.$watch('tabSet[' + tabSet + '][' + ite + '][' + tab + '].listToCompute[' + i + '].value', function() {
          return $scope.computeTab(tabSet, tab, answer.mapRepetition);
        });
        if ($scope.loading === false) {
          $scope.computeTab(tabSet, tab, answer.mapRepetition);
        }
        if (answer.value === null) {
          return $scope.tabSet[tabSet][ite][tab].isFinish = false;
        }
      }
    };
    $scope.computeTab = function(tabSet, tab, mapRepetition) {
      var answer, i, isFinish, ite, key, value, _i, _j, _k, _len, _len2, _len3, _ref, _ref2, _ref3, _results;
      isFinish = false;
      i = 0;
      while (i < $scope.tabSet[tabSet].length) {
        if ($scope.compareRepetitionMap(mapRepetition, $scope.tabSet[tabSet][i].mapRepetition)) {
          ite = i;
          break;
        }
        i++;
      }
      _ref = $scope.tabSet[tabSet][ite][tab].listToCompute;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        answer = _ref[_i];
        if (!(answer.hasValidCondition != null) || answer.hasValidCondition === true && answer.optional !== true && answer.isAggregation !== true) {
          if (answer.value === null) {
            isFinish = false;
            break;
          } else {
            isFinish = true;
          }
        }
      }
      if (isFinish !== $scope.tabSet[tabSet][ite][tab].isFinish) {
        $scope.tabSet[tabSet][ite][tab].isFinish = isFinish;
        if (isFinish === true) {
          if (!($scope.tabSet[tabSet][ite].master != null) || $scope.tabSet[tabSet][ite].master > tab) {
            $scope.tabSet[tabSet][ite].master = tab;
          }
        } else if ($scope.tabSet[tabSet][ite].master === tab) {
          delete $scope.tabSet[tabSet][ite].master;
          _ref2 = Object.keys($scope.tabSet[tabSet][ite]);
          for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
            key = _ref2[_j];
            if (key !== '$$hashKey' && key !== 'master' && key !== 'active' && key !== 'mapRepetition') {
              value = $scope.tabSet[tabSet][ite][key];
              if (value.isFinish === true) {
                if (!($scope.tabSet[tabSet][ite].master != null) || parseFloat($scope.tabSet[tabSet][ite].master) > parseFloat(key)) {
                  $scope.tabSet[tabSet][ite].master = parseFloat(key);
                }
              }
            }
          }
        }
        if ($scope.loading === true && ($scope.tabSet[tabSet][ite].master != null)) {
          _ref3 = Object.keys($scope.tabSet[tabSet][ite]);
          _results = [];
          for (_k = 0, _len3 = _ref3.length; _k < _len3; _k++) {
            key = _ref3[_k];
            _results.push(key !== '$$hashKey' && key !== 'master' && key !== 'active' && key !== 'mapRepetition' ? parseFloat(key) === parseFloat($scope.tabSet[tabSet][ite].master) ? $scope.tabSet[tabSet][ite][key].active = true : $scope.tabSet[tabSet][ite][key].active = false : void 0);
          }
          return _results;
        }
      }
    };
    $scope.getTab = function(tabSet, tab, mapRepetition) {
      var tabSetToTest, _i, _len, _ref, _ref2;
      if (mapRepetition == null) {
        mapRepetition = null;
      }
      if ($scope.loading === true || !($scope.tabSet[tabSet] != null)) {
        $scope.addTabSet(tabSet, tab, mapRepetition);
      }
      _ref = $scope.tabSet[tabSet];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        tabSetToTest = _ref[_i];
        if ($scope.compareRepetitionMap(mapRepetition, tabSetToTest.mapRepetition)) {
          if (!(tabSetToTest[tab] != null)) {
            tabSetToTest[tab] = {};
            tabSetToTest[tab].active = (_ref2 = parseFloat(tab) === 1) != null ? _ref2 : {
              "true": false
            };
            return tabSetToTest[tab];
          }
          return tabSetToTest[tab];
        }
      }
      return null;
    };
    $scope.tabIsMaster = function(tabSet, tab, mapRepetition) {
      var tabSetToTest, _i, _len, _ref;
      if (mapRepetition == null) {
        mapRepetition = null;
      }
      if ($scope.loading === true || !($scope.tabSet[tabSet] != null)) {
        return false;
      }
      _ref = $scope.tabSet[tabSet];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        tabSetToTest = _ref[_i];
        if ($scope.compareRepetitionMap(mapRepetition, tabSetToTest.mapRepetition)) {
          if (tabSetToTest.master === parseFloat(tab)) {
            return true;
          }
          return false;
        }
      }
      return false;
    };
    $scope.getQuestionSetLocker = function(code) {
      if ($scope.datalocker[code] != null) {
        return $scope.datalocker[code];
      }
      return null;
    };
    $scope.getQuestionSetValidator = function(code) {
      if ($scope.dataValidator[code] != null) {
        return $scope.dataValidator[code];
      }
      return null;
    };
    $scope.isQuestionLocked = function(code) {
      var key, question, questionSet, result, _i, _j, _len, _len2, _ref, _ref2, _results;
      _ref = Object.keys($scope.mapQuestionSet);
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        key = _ref[_i];
        if (key !== '$$hashKey') {
          questionSet = $scope.mapQuestionSet[key];
          if (questionSet.questionSetDTO.code === code) {
            result = $scope.foundBasicParent(questionSet);
            return (result.datalocker != null) && result.datalocker.identifier !== $scope.$root.currentPerson.identifier;
          }
          if (questionSet.questionSetDTO.questions != null) {
            _ref2 = questionSet.questionSetDTO.questions;
            for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
              question = _ref2[_j];
              if (question.code === code) {
                result = $scope.foundBasicParent(questionSet);
                return (result.datalocker != null) && result.datalocker.identifier !== $scope.$root.currentPerson.identifier;
              }
            }
          }
        }
      }
      return _results;
    };
    $scope.isQuestionValidate = function(code) {
      if ($scope.getValidatorByQuestionCode(code) != null) {
        return true;
      }
      return false;
    };
    $scope.getValidatorByQuestionCode = function(code) {
      var key, question, questionSet, _i, _j, _len, _len2, _ref, _ref2;
      _ref = Object.keys($scope.mapQuestionSet);
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        key = _ref[_i];
        if (key !== '$$hashKey') {
          questionSet = $scope.mapQuestionSet[key];
          if (questionSet.questionSetDTO.code === code) {
            return $scope.foundBasicParent(questionSet).dataValidator;
          }
          if (questionSet.questionSetDTO.questions != null) {
            _ref2 = questionSet.questionSetDTO.questions;
            for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
              question = _ref2[_j];
              if (question.code === code) {
                return $scope.foundBasicParent(questionSet).dataValidator;
              }
            }
          }
        }
      }
      return null;
    };
    $scope.lockQuestionSet = function(code, lock) {
      if (lock === true) {
        if (!($scope.datalocker[code] != null)) {
          $scope.datalocker[code] = $scope.$root.currentPerson;
        }
        return $scope.mapQuestionSet[code].questionSetDTO.datalocker = $scope.$root.currentPerson;
      } else {
        if ($scope.datalocker[code] != null) {
          delete $scope.datalocker[code];
        }
        return $scope.mapQuestionSet[code].questionSetDTO.datalocker = null;
      }
    };
    $scope.validateQuestionSet = function(code, lock) {
      if (lock === true) {
        if (!($scope.dataValidator[code] != null)) {
          $scope.dataValidator[code] = $scope.$root.currentPerson;
        }
        if ($scope.mapQuestionSet[code] != null) {
          return $scope.mapQuestionSet[code].questionSetDTO.dataValidator = $scope.$root.currentPerson;
        } else {
          console.log("not found " + code + " into $scope.mapQuestionSet : ");
          return console.log($scope.mapQuestionSet);
        }
      } else {
        if ($scope.dataValidator[code] != null) {
          delete $scope.dataValidator[code];
        }
        return $scope.mapQuestionSet[code].questionSetDTO.dataValidator = null;
      }
    };
    $scope.foundBasicParent = function(questionSet) {
      if (!(questionSet.parent != null)) {
        return questionSet.questionSetDTO;
      }
      return $scope.foundBasicParent(questionSet.parent);
    };
    $scope.verifyQuestionSet = function(code, verification) {
      return $scope.dataVerification[code] = verification;
    };
    return $scope.getQuestionSetVerification = function(code) {
      if ($scope.dataVerification[code] != null) {
        return $scope.dataVerification[code];
      }
      return null;
    };
  };
  if (($route.current.params.form != null)) {
    return $scope.init($route.current.params.form);
  }
});angular.module('app.controllers').controller("VerificationArchiveCtrl", function($scope, displayLittleFormMenu, modalService, downloadService, messageFlash, $window, ngTableParams, $filter) {
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  modalService.show(modalService.LOADING);
  downloadService.getJson("/awac/verification/archivedRequests", function(result) {
    if (!result.success) {
      return modalService.close(modalService.LOADING);
    } else {
      $scope.requests = result.data.list;
      $scope.displayTable();
      return modalService.close(modalService.LOADING);
    }
  });
  $scope.displayTable = function() {
    if (!$scope.requests) {
      $scope.requests = [];
    }
    if ($scope.tableParams != null) {
      return $scope.tableParams.reload();
    } else {
      return $scope.tableParams = new ngTableParams({
        page: 1,
        count: 100,
        sorting: {
          code: "asc"
        }
      }, {
        total: 0,
        getData: function($defer, params) {
          var orderedData;
          orderedData = $filter("orderBy")($scope.requests, params.orderBy());
          $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
          return params.total($scope.requests.length);
        }
      });
    }
  };
  return $scope.downloadReport = function(request) {
    var url;
    url = '/awac/file/download/' + request.verificationSuccessFileId;
    return $window.open(url);
  };
});angular.module('app.controllers').controller("UserDataCtrl", function($scope, downloadService, translationService, messageFlash, modalService) {
  $scope.isLoading = false;
  $scope.identifierInfo = {
    fieldTitle: "USER_IDENTIFIER",
    disabled: true,
    field: $scope.$root.currentPerson.identifier
  };
  $scope.passwordInfo = {
    fieldTitle: "USER_PASSWORD",
    fieldType: "password",
    disabled: true,
    field: "*****"
  };
  $scope.lastNameInfo = {
    fieldTitle: "USER_LASTNAME",
    validationRegex: "^.{1,255}$",
    validationMessage: "USER_LASTNAME_WRONG_LENGTH",
    field: $scope.$root.currentPerson.lastName,
    hideIsValidIcon: true,
    isValid: true,
    focus: function() {
      return true;
    }
  };
  $scope.firstNameInfo = {
    fieldTitle: "USER_FIRSTNAME",
    fieldType: "text",
    validationRegex: "^.{1,255}$",
    validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
    field: $scope.$root.currentPerson.firstName,
    hideIsValidIcon: true,
    isValid: true
  };
  $scope.emailInfo = {
    fieldTitle: "USER_EMAIL",
    disabled: true,
    field: $scope.$root.currentPerson.email
  };
  $scope.allFieldValid = function() {
    if ($scope.lastNameInfo.isValid && $scope.firstNameInfo.isValid) {
      return true;
    }
    return false;
  };
  $scope.$root.refreshUserData();
  $scope.send = function() {
    var data;
    if (!$scope.allFieldValid) {
      return false;
    }
    $scope.isLoading = true;
    data = {
      identifier: $scope.identifierInfo.field,
      lastName: $scope.lastNameInfo.field,
      firstName: $scope.firstNameInfo.field,
      email: $scope.emailInfo.field
    };
    downloadService.postJson('/awac/user/profile/save', data, function(result) {
      if (result.success) {
        messageFlash.displaySuccess("CHANGES_SAVED");
        $scope.$root.currentPerson.lastName = $scope.lastNameInfo.field;
        $scope.$root.currentPerson.firstName = $scope.firstNameInfo.field;
        return $scope.isLoading = false;
      } else {
        return $scope.isLoading = false;
      }
    });
    return false;
  };
  $scope.setNewEmail = function(newEmail) {
    $scope.emailInfo.field = newEmail;
    return $scope.$root.currentPerson.email = newEmail;
  };
  $scope.changeEmail = function() {
    return modalService.show(modalService.EMAIL_CHANGE, {
      oldEmail: $scope.emailInfo.field,
      cb: $scope.setNewEmail
    });
  };
  $scope.changePassword = function() {
    return modalService.show(modalService.PASSWORD_CHANGE, {});
  };
  $scope.toForm = function() {
    return $scope.$root.navToLastFormUsed();
  };
  $scope.anonymous = {
    loginInfo: {
      fieldTitle: "LOGIN_FORM_LOGIN_FIELD_TITLE",
      fieldType: "text",
      placeholder: "LOGIN_FORM_LOGIN_FIELD_PLACEHOLDER",
      validationRegex: "^\\S{5,20}$",
      validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH",
      field: $scope.$root.currentPerson.identifier,
      isValid: false,
      focus: function() {
        return true;
      }
    },
    passwordInfo: {
      fieldTitle: "LOGIN_FORM_PASSWORD_FIELD_TITLE",
      fieldType: "password",
      validationRegex: "^\\S{5,20}$",
      validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
      field: "",
      isValid: false
    },
    lastNameInfo: {
      fieldTitle: "USER_LASTNAME",
      validationRegex: "^.{1,255}$",
      validationMessage: "USER_LASTNAME_WRONG_LENGTH",
      field: $scope.$root.currentPerson.lastName,
      isValid: true
    },
    firstNameInfo: {
      fieldTitle: "USER_FIRSTNAME",
      fieldType: "text",
      validationRegex: "^.{1,255}$",
      validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
      field: $scope.$root.currentPerson.firstName,
      isValid: true
    },
    emailInfo: {
      fieldTitle: "USER_EMAIL",
      validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
      validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT",
      field: $scope.$root.currentPerson.email
    }
  };
  $scope.anonymousFieldValid = function() {
    var field, key, _i, _len, _ref;
    _ref = Object.keys($scope.anonymous);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if (key !== '$$hashKey') {
        field = $scope.anonymous[key];
        if (!field.isValid || field.isValid === false) {
          return false;
        }
      }
    }
    return true;
  };
  $scope.sendAnonymous = function() {
    var data;
    if (!$scope.anonymousFieldValid()) {
      return false;
    }
    $scope.isLoading = true;
    data = {
      identifier: $scope.anonymous.loginInfo.field,
      password: $scope.anonymous.passwordInfo.field,
      lastName: $scope.anonymous.lastNameInfo.field,
      firstName: $scope.anonymous.firstNameInfo.field,
      email: $scope.anonymous.emailInfo.field
    };
    downloadService.postJson('/awac/user/profile/anonymous/save', data, function(result) {
      if (result.success) {
        messageFlash.displaySuccess("CHANGES_SAVED");
        messageFlash.displaySuccess("Please login with your new credentials");
        $scope.$root.nav('/login');
        $scope.$root.currentPerson = null;
        $scope.$root.periodSelectedKey = null;
        $scope.$root.scopeSelectedId = null;
        return $scope.isLoading = false;
      } else {
        return $scope.isLoading = false;
      }
    });
    return false;
  };
  return $scope.isAnonymousUser = function() {
    var _ref;
    console.log(angular.copy($scope.$root.currentPerson));
    console.log(angular.copy($scope.$root.currentPerson.identifier));
    if (((_ref = $scope.$root.currentPerson) != null ? _ref.identifier.indexOf("anonymous_user") : void 0) !== -1) {
      console.log("currentUser is an anonymous user");
      return true;
    } else {
      console.log("currentUser is not anonymous user");
      return false;
    }
  };
});angular.module('app.controllers').controller("UserManagerCtrl", function($scope, translationService, modalService, downloadService, messageFlash) {
  $scope.title = translationService.get('USER_MANAGER_TITLE');
  $scope.isLoading = {};
  $scope.isLoading['admin'] = {};
  $scope.isLoading['isActive'] = {};
  modalService.show(modalService.LOADING);
  downloadService.getJson('awac/organization/getMyOrganization', function(result) {
    if (!result.success) {
      messageFlash.displayError(translationService.get('UNABLE_LOAD_DATA'));
      return modalService.close(modalService.LOADING);
    } else {
      modalService.close(modalService.LOADING);
      $scope.organization = result.data;
      $scope.getUserList = function() {
        return $scope.organization.users;
      };
      $scope.inviteUser = function() {
        return modalService.show(modalService.INVITE_USER);
      };
      $scope.getMyself = function() {
        return $scope.$root.currentPerson;
      };
      $scope.activeUser = function(user) {
        var data;
        if ($scope.getMyself().isAdmin === true && $scope.getMyself().email !== user.email) {
          data = {};
          data.identifier = user.identifier;
          data.isActive = !user.isActive;
          $scope.isLoading['isActive'][user.email] = true;
          return downloadService.postJson("/awac/user/activeAccount", data, function(result) {
            if (result.success) {
              user.isActive = !user.isActive;
              return $scope.isLoading['isActive'][user.email] = false;
            } else {
              return $scope.isLoading['isActive'][user.email] = false;
            }
          });
        }
      };
      $scope.isAdminUser = function(user) {
        var data;
        if ($scope.getMyself().isAdmin === true && $scope.getMyself().email !== user.email && user.isActive === true) {
          data = {};
          data.identifier = user.identifier;
          data.isAdmin = !user.isAdmin;
          $scope.isLoading['admin'][user.email] = true;
          return downloadService.postJson("/awac/user/isAdminAccount", data, function(result) {
            if (result.success) {
              return $scope.isLoading['admin'][user.email] = false;
            } else {
              return $scope.isLoading['admin'][user.email] = false;
            }
          });
        }
      };
      return $scope.isMainVerifier = function(user) {
        var data;
        if ($scope.getMyself().isAdmin === true && $scope.getMyself().email !== user.email && user.isActive === true) {
          data = {};
          data.identifier = user.identifier;
          data.isMainVerifier = !user.isMainVerifier;
          $scope.isLoading['isMainVerifier'][user.email] = true;
          return downloadService.postJson("/awac/user/isMainVerifier", data, function(result) {
            return $scope.isLoading['isMainVerifier'][user.email] = false;
          });
        }
      };
    }
  });
  return $scope.toForm = function() {
    if ($scope.$root.mySites.length > 0) {
      $scope.$root.scopeSelectedId = $scope.$root.mySites[0].id;
      $scope.$root.periodSelectedKey = $scope.$root.mySites[0].listPeriodAvailable[0].key;
      return $scope.$root.navToLastFormUsed();
    } else {
      $scope.$root.scopeSelectedId = void 0;
      $scope.$root.periodSelectedKey = void 0;
      return $scope.$root.nav('/noScope');
    }
  };
});angular.module('app.controllers').controller("AdminActionAdviceCtrl", function($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile) {
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  modalService.show(modalService.LOADING);
  return downloadService.getJson("/awac/admin/advice/load", function(result) {
    modalService.close(modalService.LOADING);
    return console.log("bvlabla");
  });
});angular.module('app.controllers').controller("RegistrationCtrl", function($scope, downloadService, messageFlash, $compile, $timeout, modalService, translationService, $routeParams) {
  downloadService.postJson('/awac/logout', null, function(result) {});
  $scope.loading = false;
  $scope.tabActive = [];
  $scope.enterEvent = function() {
    if ($scope.tabActive[0] === true) {
      return $scope.send();
    }
  };
  $scope.fields = {
    loginInfo: {
      fieldTitle: "LOGIN_FORM_LOGIN_FIELD_TITLE",
      fieldType: "text",
      placeholder: "LOGIN_FORM_LOGIN_FIELD_PLACEHOLDER",
      validationRegex: "^\\S{5,20}$",
      validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH",
      field: "",
      isValid: false,
      focus: function() {
        return true;
      }
    },
    passwordInfo: {
      fieldTitle: "LOGIN_FORM_PASSWORD_FIELD_TITLE",
      fieldType: "password",
      validationRegex: "^\\S{5,20}$",
      validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
      field: "",
      isValid: false
    },
    lastNameInfo: {
      fieldTitle: "USER_LASTNAME",
      validationRegex: "^.{1,255}$",
      validationMessage: "USER_LASTNAME_WRONG_LENGTH",
      isValid: true
    },
    firstNameInfo: {
      fieldTitle: "USER_FIRSTNAME",
      fieldType: "text",
      validationRegex: "^.{1,255}$",
      validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
      isValid: true
    },
    emailInfo: {
      fieldTitle: "USER_EMAIL",
      validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
      validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
    }
  };
  $scope.connectionFieldValid = function() {
    var field, key, _i, _len, _ref;
    _ref = Object.keys($scope.fields);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if (key !== '$$hashKey') {
        field = $scope.fields[key];
        if (!field.isValid || field.isValid === false) {
          return false;
        }
      }
    }
    return true;
  };
  $scope.send = function() {
    var data, personDTO;
    $scope.isLoading = true;
    personDTO = {
      identifier: $scope.fields.loginInfo.field,
      lastName: $scope.fields.lastNameInfo.field,
      firstName: $scope.fields.firstNameInfo.field,
      email: $scope.fields.emailInfo.field
    };
    data = {
      person: personDTO,
      password: $scope.fields.passwordInfo.field,
      key: $routeParams.key
    };
    downloadService.postJson('/awac/register', data, function(result) {
      if (result.success) {
        messageFlash.displaySuccess(translationService.get('REGISTRATION_SUCCESS'));
        $scope.isLoading = false;
        console.log('registration');
        return $scope.$root.nav('/login');
      } else {
        messageFlash.displaySuccess(translationService.get('REGISTRATION_FAILED'));
        return $scope.isLoading = false;
      }
    });
    return false;
  };
  $scope.injectRegistrationDirective = function() {
    var directive, directiveName;
    if ($scope.$root != null) {
      if ($scope.$root.instanceName === 'enterprise') {
        directiveName = "mm-awac-registration-enterprise";
      } else if ($scope.$root.instanceName === 'municipality') {
        directiveName = "mm-awac-registration-municipality";
      }
      directive = $compile("<" + directiveName + "></" + directiveName + ">")($scope);
      return $('.inject-registration-form').append(directive);
    }
  };
  return $timeout(function() {
    return $scope.injectRegistrationDirective();
  }, 0);
});angular.module('app.controllers').controller("AdminCtrl", function($scope, downloadService) {
  $scope.notifications = [];
  return downloadService.getJson("admin/get_notifications", function(dto) {
    if (dto != null) {
      $scope.notifications = dto.notifications;
    }
    return;
  });
});angular.module('app.controllers').controller("VerificationSubmitCtrl", function($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile, $window) {
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  $scope.selectedRequest = null;
  $scope.form = null;
  $scope.requestSelected = null;
  modalService.show(modalService.LOADING);
  downloadService.getJson('/awac/verification/verificationRequestsVerifiedToConfirm', function(result) {
    if (!result.success) {
      return modalService.close(modalService.LOADING);
    } else {
      modalService.close(modalService.LOADING);
      $scope.requests = result.data.list;
      return $scope.displayTable();
    }
  });
  $scope.displayTable = function() {
    if (!$scope.requests) {
      $scope.requests = [];
    }
    if ($scope.tableParams != null) {
      return $scope.tableParams.reload();
    } else {
      return $scope.tableParams = new ngTableParams({
        page: 1,
        count: 100
      }, {
        counts: [],
        total: 1,
        getData: function($defer, params) {
          var orderedData;
          orderedData = $filter("orderBy")($scope.requests, params.orderBy());
          $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
          params.total(orderedData.length);
          return;
        }
      });
    }
  };
  $scope.selectRequest = function(request) {
    var directiveName;
    $('.injectForm:first').empty();
    $('.injectFormMenu:first').empty();
    if ($scope.directiveMenu != null) {
      $scope.directiveMenu.remove();
    }
    if ($scope.directive != null) {
      $scope.directive.remove();
    }
    if (request != null) {
      $scope.requestSelected = request;
    }
    $scope.selectedRequest = $scope.requestSelected;
    $scope.$root.periodSelectedKey = $scope.requestSelected.period.key;
    $scope.$root.scopeSelectedId = $scope.requestSelected.scope.id;
    if (!$scope.form) {
      if ($scope.requestSelected.organizationCustomer.interfaceName === 'enterprise') {
        $scope.form = 'TAB2';
      }
      if ($scope.requestSelected.organizationCustomer.interfaceName === 'municipality') {
        $scope.form = 'TAB_C1';
      }
    }
    if ($scope.form === '/results') {
      directiveName = "<div ng-include=\"'$/angular/views/results.html'\" ng-controller=\"ResultsCtrl\"></div>";
    } else {
      directiveName = "<div ng-include=\"'$/angular/views/enterprise/" + $scope.form + ".html'\" ng-init=\"init('" + $scope.form + "')\" ng-controller=\"FormCtrl\"></div>";
    }
    $scope.directive = $compile(directiveName)($scope);
    $('.injectForm:first').append($scope.directive);
    $scope.directiveMenu = $compile("<mm-awac-" + $scope.requestSelected.organizationCustomer.interfaceName + "-menu form=\"" + $scope.form + "\"></mm-awac-" + $scope.requestSelected.organizationCustomer.interfaceName + "-menu>")($scope);
    $scope.displayMenu = true;
    $('.injectFormMenu:first').append($scope.directiveMenu);
    return $scope.$root.mySites = [request.scope];
  };
  $scope.isMenuCurrentlySelected = function(target) {
    if ('/form/' + $scope.form === target || $scope.form === target) {
      return true;
    }
    return false;
  };
  $scope.downloadFile = function() {
    var url;
    url = '/awac/file/download/' + $scope.selectedRequest.verificationSuccessFileId;
    return $window.open(url);
  };
  $scope.validVerificationFinalization = function(valid) {
    var data, newStatus;
    if (valid === true) {
      if ($scope.requestSelected.status === 'VERIFICATION_STATUS_WAIT_VERIFICATION_CONFIRMATION_SUCCESS') {
        newStatus = 'VERIFICATION_STATUS_WAIT_CUSTOMER_VERIFIED_CONFIRMATION';
      } else {
        newStatus = 'VERIFICATION_STATUS_CORRECTION';
      }
    } else {
      newStatus = 'VERIFICATION_STATUS_VERIFICATION';
    }
    data = {
      url: "/awac/verification/setStatus",
      successMessage: "CHANGES_SAVED",
      title: "VALID_REQUEST",
      data: {
        scopeId: $scope.selectedRequest.scope.id,
        periodKey: $scope.selectedRequest.period.key,
        newStatus: newStatus
      },
      afterSave: function() {
        return $scope.removeRequest();
      }
    };
    return modalService.show(modalService.PASSWORD_CONFIRMATION, data);
  };
  $scope.navTo = function(target) {
    var form;
    form = target.replace('/form/', '');
    $scope.form = form;
    return $scope.selectRequest();
  };
  $scope.consultFinalComment = function() {
    var data;
    data = {
      comment: $scope.selectedRequest.verificationRejectedComment
    };
    return modalService.show(modalService.VERIFICATION_FINALIZATION_VISUALIZATION, data);
  };
  $scope.consultEvent = function() {
    var data;
    data = {
      organizationCustomer: $scope.requestSelected.organizationCustomer
    };
    return modalService.show(modalService.CONSULT_EVENT, data);
  };
  return $scope.removeRequest = function() {
    var i, request, _i, _len, _ref;
    i = 0;
    _ref = $scope.requests;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      request = _ref[_i];
      if (request.id === $scope.requestSelected.id) {
        $scope.requests.splice(i, 1);
      }
      i++;
    }
    $scope.displayTable();
    $('.injectForm:first').empty();
    $('.injectFormMenu:first').empty();
    return $scope.requestSelected = null;
  };
});angular.module('app.controllers').controller("VerificationRegistrationCtrl", function($scope, downloadService, modalService, messageFlash, $routeParams, translationService) {
  $scope.isLoading = false;
  $scope.fields = {
    identifierInfo: {
      fieldTitle: "USER_IDENTIFIER",
      inputName: 'identifier',
      validationRegex: "[a-zA-Z0-9-]{5,20}",
      validationMessage: "IDENTIFIER_CHECK_WRONG"
    },
    lastNameInfo: {
      fieldTitle: "USER_LASTNAME",
      inputName: 'lastName',
      validationRegex: "^.{1,255}$",
      validationMessage: "USER_LASTNAME_WRONG_LENGTH"
    },
    firstNameInfo: {
      fieldTitle: "USER_FIRSTNAME",
      inputName: 'firstName',
      fieldType: "text",
      validationRegex: "^.{1,255}$",
      validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
      focus: function() {
        return true;
      }
    },
    emailInfo: {
      fieldTitle: "USER_EMAIL",
      inputName: 'email',
      validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
      validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
    },
    passwordInfo: {
      fieldTitle: "USER_PASSWORD",
      inputName: 'password',
      fieldType: "password",
      validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
      validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
    },
    passwordConfirmInfo: {
      fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE",
      inputName: 'password',
      fieldType: "password",
      validationFct: $scope.validatePasswordConfirmField,
      validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
    },
    organizationNameInfo: {
      fieldTitle: "ORGANIZATION_NAME",
      fieldType: "text",
      validationRegex: "^.{1,255}$",
      validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH"
    }
  };
  $scope.allFieldValid = function() {
    var key, _i, _len, _ref;
    _ref = Object.keys($scope.fields);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if (key !== '$$hashKey') {
        if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
          return false;
        }
      }
    }
    return true;
  };
  return $scope.registration = function() {
    var data;
    if ($scope.allFieldValid()) {
      $scope.isLoading = true;
      data = {};
      data.person = {};
      data.person.email = $scope.fields.emailInfo.field;
      data.person.identifier = $scope.fields.identifierInfo.field;
      data.person.firstName = $scope.fields.firstNameInfo.field;
      data.person.lastName = $scope.fields.lastNameInfo.field;
      data.password = $scope.fields.passwordInfo.field;
      data.organizationName = $scope.fields.organizationNameInfo.field;
      data.person.defaultLanguage = $scope.$root.language;
      data.key = $routeParams.key;
      downloadService.postJson('/awac/registration/validator', data, function(result) {
        if (result.success) {
          $scope.$root.loginSuccess(result.data);
          messageFlash.displaySuccess(translationService.get("CONNECTION_MESSAGE_SUCCESS"));
          return $scope.isLoading = false;
        } else {
          return $scope.isLoading = false;
        }
      });
      return false;
    }
  };
});angular.module('app.controllers').controller("NoScopeCtrl", function($scope, displayLittleFormMenu) {
  return $scope.displayLittleFormMenu = displayLittleFormMenu;
});angular.module('app.controllers').controller("VerificationManageCtrl", function($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, $filter, ngTableParams) {
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  $scope.loadingRequest = [];
  modalService.show(modalService.LOADING);
  downloadService.getJson("/awac/verification/requestsToManage", function(result) {
    if (!result.success) {
      return modalService.close(modalService.LOADING);
    } else {
      $scope.requests = result.data.list;
      $scope.displayTable();
      return modalService.close(modalService.LOADING);
    }
  });
  $scope.displayTable = function() {
    if (!$scope.requests) {
      $scope.requests = [];
    }
    if ($scope.tableParams != null) {
      return $scope.tableParams.reload();
    } else {
      return $scope.tableParams = new ngTableParams({
        page: 1,
        count: 100,
        sorting: {
          code: "asc"
        }
      }, {
        total: 0,
        getData: function($defer, params) {
          var orderedData;
          orderedData = $filter("orderBy")($scope.requests, params.orderBy());
          $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
          return params.total($scope.requests.length);
        }
      });
    }
  };
  $scope.changeStatus = function(request, newStatus) {
    var dto;
    dto = {
      scopeId: request.scope.id,
      periodKey: request.period.key,
      newStatus: newStatus
    };
    $scope.loadingRequest[request.id] = true;
    return downloadService.postJson("/awac/verification/setStatus", dto, function(result) {
      var i, r, _i, _len, _ref;
      if (!result.success) {
        return $scope.loadingRequest[request.id] = false;
      } else {
        request.status = newStatus;
        $scope.loadingRequest[request.id] = false;
        if (newStatus === 'VERIFICATION_STATUS_REJECTED') {
          i = 0;
          _ref = $scope.requests;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            r = _ref[_i];
            if (r.id === request.id) {
              $scope.requests.splice(i, 1);
            }
            i++;
          }
        }
        return $scope.displayTable();
      }
    });
  };
  $scope.assignRequest = function(request) {
    var params;
    params = {
      request: request
    };
    return modalService.show(modalService.VERIFICATION_ASSIGN, params);
  };
  return $scope.addKey = function() {
    var dto;
    dto = {
      key: angular.copy($scope.keyToInject)
    };
    $scope.addKeyLoading = true;
    $scope.keyToInject = "";
    return downloadService.postJson("/awac/verification/addRequestByKey", dto, function(result) {
      if (!result.success) {
        return $scope.addKeyLoading = false;
      } else {
        $scope.requests.push(result.data);
        $scope.displayTable();
        return $scope.addKeyLoading = false;
      }
    });
  };
});angular.module('app.controllers').controller("SiteManagerCtrl", function($scope, translationService, modalService, downloadService, messageFlash) {
  $scope.isLoading = {};
  if ($scope.$root.periods != null) {
    $scope.assignPeriod = $scope.$root.periods[0].key;
  } else {
    $scope.$watch('$root.periods', function() {
      return $scope.assignPeriod = $scope.$root.periods[0].key;
    });
  }
  $scope.isPeriodChecked = {};
  $scope.selectedPeriodForEvent = $scope.$root.periods[0].key;
  modalService.show(modalService.LOADING);
  return downloadService.getJson('awac/organization/getMyOrganization', function(result) {
    if (!result.success) {
      return modalService.close(modalService.LOADING);
    } else {
      modalService.close(modalService.LOADING);
      $scope.organization = result.data;
      $scope.$watchCollection('assignPeriod', function() {
        return $scope.refreshPeriod();
      });
      $scope.refreshPeriod = function() {
        var site, _i, _len, _ref, _results;
        _ref = $scope.organization.sites;
        _results = [];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          site = _ref[_i];
          _results.push($scope.isPeriodChecked[site.id] = $scope.periodAssignTo(site));
        }
        return _results;
      };
      $scope.toForm = function() {
        if ($scope.$root.mySites.length > 0) {
          $scope.$root.scopeSelectedId = $scope.$root.mySites[0].id;
          $scope.$root.periodSelectedKey = $scope.$root.mySites[0].listPeriodAvailable[0].key;
          return $scope.$root.navToLastFormUsed();
        } else {
          $scope.$root.scopeSelectedId = void 0;
          $scope.$root.periodSelectedKey = void 0;
          return $scope.$root.nav('/noScope');
        }
      };
      $scope.getSiteList = function() {
        return $scope.organization.sites;
      };
      $scope.editOrCreateSite = function(site) {
        var params;
        params = {};
        if (site != null) {
          params.site = site;
        }
        params.organization = $scope.organization;
        params.refreshMySites = function() {
          $scope.refreshMySites();
          return $scope.refreshPeriod();
        };
        return modalService.show(modalService.EDIT_SITE, params);
      };
      $scope.addUsers = function(site) {
        var params;
        params = {};
        if (site != null) {
          params.site = site;
        }
        params.organization = $scope.organization;
        return modalService.show(modalService.ADD_USER_SITE, params);
      };
      $scope.periodAssignTo = function(site) {
        var period, _i, _len, _ref;
        if (site.listPeriodAvailable != null) {
          _ref = site.listPeriodAvailable;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            period = _ref[_i];
            if (period.key === $scope.assignPeriod) {
              return true;
            }
          }
        }
        return false;
      };
      $scope.assignPeriodToSite = function(site) {
        var data;
        $scope.isLoading[site.id] = true;
        data = {};
        data.periodKeyCode = $scope.assignPeriod;
        data.siteId = site.id;
        data.assign = !$scope.periodAssignTo(site);
        return downloadService.postJson('awac/site/assignPeriodToSite', data, function(result) {
          $scope.isLoading[site.id] = false;
          if (result.success) {
            site.listPeriodAvailable = result.data.periodsList;
            return $scope.refreshMySites();
          }
        });
      };
      return $scope.refreshMySites = function() {
        var mySites, person, site, _i, _j, _len, _len2, _ref, _ref2;
        mySites = [];
        _ref = $scope.organization.sites;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          site = _ref[_i];
          if (site.listPersons != null) {
            _ref2 = site.listPersons;
            for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
              person = _ref2[_j];
              if (person.identifier === $scope.$root.currentPerson.identifier) {
                mySites.push(site);
              }
            }
          }
        }
        console.log("voilà tes nouveaux sites : ");
        console.log($scope.$root.mySites);
        return $scope.$root.mySites = mySites;
      };
    }
  });
});angular.module('app.directives').run(function($templateCache) {$templateCache.put('$/angular/views/organization_manager.html', "<div><div class=\"menu_close\" ng-click=\"toForm()\"></div><h1 ng-bind-html=\"'ORGANIZATION_MANAGER_TITLE' | translateText\"></h1><div class=\"site_manager\"><br><h4 ng-bind-html=\"'ORGANIZATION_MANAGER_PROPERTIES_TITLE' | translateText\"></h4><div class=\"field_form\" style=\"width: 600px;\"><mm-awac-modal-field-text ng-info=\"nameInfo\"></mm-awac-modal-field-text><div class=\"field_row\" ng-hide=\"$root.instanceName == 'verification'\"><div class=\"field_cell\" ng-bind-html=\"'ORGANIZATION_STATISTICS_ALLOWED' | translateText\"></div><div class=\"field_cell\" ng-hide=\"$root.instanceName == 'verification' || $root.instanceName == 'admin'\"><input style=\"width: 18px;height: 18px;margin-top: 2px;\" ng-model=\"statisticsAllowed\" type=\"checkbox\"></div></div></div><div ng-hide=\"isLoading\"><button ng-disabled=\"!allFieldValid()\" ng-click=\"saveOrganization()\" ng-bind-html=\"'SAVE_BUTTON' | translate\" class=\"btn btn-primary\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"><div ng-hide=\"$root.instanceName == 'verification' || $root.instanceName == 'admin'\"><br><br><h4 ng-bind-html=\"'SITE_MANAGER_EVENT_TITLE' | translateText\"></h4><div class=\"desc\" ng-bind-html=\"'SITE_MANAGER_EVENT_DESC' | translateText\"></div><br><div><span class=\"select_period\" ng-bind-html=\"'SITE_MANAGER_SELECT_PERIOD' | translateText\"></span><select ng-options=\"p.key as p.label for p in $root.periods\" ng-model=\"selectedPeriodForEvent\"></select></div><table class=\"site_table\"><tr class=\"site_table_header\"><td ng-bind-html=\"'SITE_MANAGER_EVENT_NAME' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_EVENT_DESCRIPTION' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_EVENT_PERIOD' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_EDIT_EVENT_BUTTON' | translateText\"></td></tr><tr ng-show=\"event.period.key == selectedPeriodForEvent\" ng-repeat=\"event in events\"><td>{{event.name}}</td><td>{{event.description}}</td><td>{{event.period.label}}</td><td><button title=\"{{'SITE_MANAGER_EDIT_EVENT_BUTTON' | translateText}}\" ng-click=\"editOrCreateEvent(event)\" class=\"edit_icon glyphicon glyphicon-pencil\" type=\"button\"></button></td></tr></table><button class=\"button add\" ng-click=\"editOrCreateEvent()\" ng-bind-html=\"'SITE_MANAGER_ADD_EVENT_BUTTON' | translateText\" type=\"button\"></button></div></div></div>");$templateCache.put('$/angular/views/actions.html', "<div class=\"actions\"><span><h1>{{'REDUCTION_ACTIONS' | translateText }}</h1></span><br><table class=\"actions-table\"><thead><tr><th>{{ \"REDUCTION_ACTION_TABLE_DETAILS\" | translateText }}</th><th>{{ \"REDUCTION_ACTION_TABLE_ACTIONS\" | translateText }}</th><th>{{ \"REDUCTION_ACTION_TABLE_TYPE\" | translateText }}</th><th>{{ \"REDUCTION_ACTION_TABLE_SCOPE\" | translateText }}</th><th>{{ \"REDUCTION_ACTION_TABLE_COMPLETION\" | translateText }}</th></tr></thead><tbody><tr ng-repeat-start=\"a in actions\"><td class=\"details\"><mm-plus-minus-toggle-button ng-model=\"a.opened\"></mm-plus-minus-toggle-button></td><td class=\"title\"><span class=\"text\">{{a.title}}</span></td><td class=\"type\"><span class=\"icon action_type_icon\" ng-class=\"{reducing: a.typeKey == '1', better_measure: a.typeKey == '2'}\"></span><span>&nbsp;&nbsp;</span><span class=\"text\">{{ getTypeLabel(a.typeKey) }}</span></td><td class=\"scope\"><span class=\"icon scope_type_icon\" ng-class=\"{org: a.scopeTypeKey == '1', site: a.scopeTypeKey == '2'}\"></span><span>&nbsp;&nbsp;</span><span class=\"text\">{{ getScopeName(a.scopeTypeKey, a.scopeId) }}</span></td><td class=\"realization\"><span class=\"icon action_done_icon\" ng-show=\"a.statusKey == '2'\"></span><span>&nbsp;&nbsp;</span><span class=\"text\">{{ (a.statusKey == \"2\") ? (a.completionDate | date : \"yyyy\") : getStatusLabel(a.statusKey) }}</span></td></tr><tr ng-repeat-end ng-show=\"a.opened\" class=\"details\"><td colspan=\"2\"><table><tr><td>{{ \"REDUCTION_ACTION_WEBSITE_FIELD_TITLE\" | translateText }}:</td><td><a target=\"blank\" ng-if=\"a.webSite != null\" href=\"{{a.webSite}}\">{{ a.webSite }}</a></td></tr><tr><td>{{ \"REDUCTION_ACTION_PHYSICAL_MEASURE_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\">{{ a.physicalMeasure || \"&nbsp;\" }}</div></td></tr><tr ng-if=\"a.typeKey == '1'\"><td>{{ \"REDUCTION_ACTION_GHG_BENEFIT_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\" ng-show=\"!!a.ghgBenefit\">{{ (a.ghgBenefit | numberToI18N) + \"&nbsp;\" + getGwpUnitSymbol(a.ghgBenefitUnitKey) }}</div><div class=\"box wide\" ng-hide=\"!!a.ghgBenefit\">&nbsp;</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_FINANCIAL_BENEFIT_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\" ng-show=\"!!a.financialBenefit\">{{ (a.financialBenefit | numberToI18N) + \"&nbsp;EUR\" }}</div><div class=\"box wide\" ng-hide=\"!!a.financialBenefit\">&nbsp;</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_INVESTMENT_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\" ng-show=\"!!a.investmentCost\">{{ (a.investmentCost | numberToI18N) + \"&nbsp;EUR\" }}</div><div class=\"box wide\" ng-hide=\"!!a.investmentCost\">&nbsp;</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_EXPECTED_PAYBACK_TIME_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\">{{ a.expectedPaybackTime || \"&nbsp;\" }}</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_DUE_DATE_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\">{{ a.dueDate | date: 'dd/MM/yyyy' }}</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_RESPONSIBLE_PERSON_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\">{{ a.responsiblePerson || \"&nbsp;\" }}</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_COMMENT_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\" style=\"min-height: 150px;\">{{ a.comment || \"&nbsp;\" }}</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_ATTACHMENTS_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\" ng-show=\"a.files.length &gt; 0\"><div ng-repeat=\"f in a.files\"><a ng-href=\"/awac/file/download/{{ f.id }}\">{{ f.name }}</a></div></div><div class=\"box wide\" ng-hide=\"a.files.length &gt; 0\">&nbsp;</div></td></tr></table></td><td colspan=\"4\" style=\"vertical-align: bottom; text-align: right\"><button class=\"button\" ng-click=\"updateAction(a)\" type=\"button\"><span class=\"fa fa-pencil-square-o\"></span><span>&nbsp;</span><span ng-bind-html=\"'REDUCING_ACTION_BUTTON_EDIT' | translate\"></span></button><button class=\"button\" ng-click=\"confirmDelete(a)\" type=\"button\"><span class=\"fa fa-times\"></span><span>&nbsp;</span><span ng-bind-html=\"'REDUCING_ACTION_BUTTON_DELETE' | translate\"></span></button><button class=\"button\" ng-click=\"markActionAsDone(a)\" type=\"button\" ng-show=\"a.statusKey != '2'\"><span class=\"fa fa-check-square-o\"></span><span>&nbsp;</span><span ng-bind-html=\"'REDUCING_ACTION_BUTTON_MARK_AS_DONE' | translate\"></span></button></td></tr></tbody></table><br><button class=\"button\" ng-click=\"createAction()\" type=\"button\"><span class=\"fa fa-plus-circle\"></span><span>&nbsp;</span><span ng-bind-html=\"'REDUCING_ACTION_BUTTON_CREATE' | translate\"></span></button><button class=\"button\" ng-click=\"exportActionsToXls()\" type=\"button\"><span class=\"fa fa-file-excel-o\"></span><span>&nbsp;</span><span ng-bind-html=\"'REDUCING_ACTION_BUTTON_EXPORT_TO_XLS' | translate\"></span></button></div>");$templateCache.put('$/angular/views/municipality/confidentiality_fr.html', "<h1>Calculateur commune de l'AWAC: gestion de la confidentialité de vos données</h1>");$templateCache.put('$/angular/views/municipality/help_organization_manager_fr.html', "<h1>Calculateur communes de l'AWAC: aide sur la gestion de l'organisation</h1>");$templateCache.put('$/angular/views/municipality/TAB_C2.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AC9\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_AC9' | translate\"></div></div><!--main loop--><mm-awac-repetition-name question-code=\"AC10\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC10\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC10')\"><mm-awac-question question-code=\"AC11\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC12\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC12',itLevel1).value == '8'\" question-code=\"AC13\" ng-optional=\"true\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC2002\" ng-optional=\"true\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC2003\" ng-optional=\"true\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC14\" ng-optional=\"true\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC15\" ng-optional=\"true\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC16\" ng-optional=\"true\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC17\" ng-optional=\"true\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC18\" ng-optional=\"true\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC19\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC20\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC21\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC19',itLevel1).value == '1' || getAnswer('AC19',itLevel1).value == '3'\" question-code=\"AC22\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC23\" ng-optional=\"true\" ng-repetition-map=\"itLevel1\"></mm-awac-question><!--AC24--><mm-awac-sub-title question-code=\"AC24\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"AC25\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"AC25\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('AC25', itLevel1)\"><mm-awac-question question-code=\"AC26\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"AC27\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"AC25\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"AC900\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"AC900\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('AC900', itLevel1)\"><mm-awac-question question-code=\"AC901\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"AC902\" ng-repetition-map=\"itLevel2\"><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"AC900\"></mm-awac-repetition-add-button></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-name question-code=\"AC903\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"AC903\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('AC903', itLevel1)\"><mm-awac-question question-code=\"AC904\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"AC905\" ng-repetition-map=\"itLevel2\"><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"AC903\"></mm-awac-repetition-add-button></mm-awac-question></mm-awac-repetition-question><!--AC28--><mm-awac-sub-title question-code=\"AC28\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"AC29\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"AC30\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC31\" ng-repetition-map=\"itLevel1\"></mm-awac-question><!--AC32--><mm-awac-block ng-condition=\"getAnswer('AC19',itLevel1).value == '1' || getAnswer('AC19',itLevel1).value == '3'\"><mm-awac-sub-title question-code=\"AC32\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"AC33\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"AC33\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('AC33', itLevel1)\"><mm-awac-question question-code=\"AC34\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"AC35\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"AC36\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"AC33\"></mm-awac-repetition-add-button></mm-awac-block><!--AC37--><mm-awac-sub-title question-code=\"AC37\" ng-repetition-map=\"itLevel1\"></mm-awac-sub-title><mm-awac-question question-code=\"AC38\" ng-optional=\"true\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"AC39\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"AC39\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('AC39', itLevel1)\"><mm-awac-question question-code=\"AC40\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"AC41\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"AC39\"></mm-awac-repetition-add-button><!--AC42--><mm-awac-block ng-condition=\"getAnswer('AC19',itLevel1).value == '1' || getAnswer('AC19',itLevel1).value == '3'\"><mm-awac-sub-title question-code=\"AC42\" ng-repetition-map=\"itLevel1\"></mm-awac-sub-title><mm-awac-question question-code=\"AC43\" ng-optional=\"true\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"AC5000\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"AC5000\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('AC5000', itLevel1)\"><mm-awac-question question-code=\"AC5001\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"AC5002\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"AC5000\"></mm-awac-repetition-add-button></mm-awac-block></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC10\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><!--éclairage public AS52--><mm-awac-section title-code=\"AC52\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_AC52' | translate\"></div></div><mm-awac-question question-code=\"AC53\" ng-optional=\"true\"></mm-awac-question><mm-awac-sub-title question-code=\"AC2000\"></mm-awac-sub-title><mm-awac-question question-code=\"AC54\"></mm-awac-question><mm-awac-question question-code=\"AC55\"></mm-awac-question><mm-awac-sub-title question-code=\"AC2001\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"AC56\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC56\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC56')\"><mm-awac-question question-code=\"AC57\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC58\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC59\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"AC56\"></mm-awac-repetition-add-button></mm-awac-section></div></div>");$templateCache.put('$/angular/views/municipality/help_results_fr.html', "<h1>Calculateur commune de l'AWAC: aide sur la présentation des résultats</h1>");$templateCache.put('$/angular/views/municipality/help_user_data_fr.html', "<h1>Calculateur commune de l'AWAC: aide sur le profil individuel</h1>");$templateCache.put('$/angular/views/municipality/help_user_manager_fr.html', "<h1>Calculateur commune de l'AWAC: aide sur la gestion des utilisateurs</h1>");$templateCache.put('$/angular/views/municipality/TAB_C1.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AC1\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_AC1' | translate\"></div></div><mm-awac-sub-title question-code=\"AC2\"></mm-awac-sub-title><mm-awac-question question-code=\"AC3\"></mm-awac-question><mm-awac-question question-code=\"AC6\"></mm-awac-question><mm-awac-question question-code=\"AC7\"></mm-awac-question><mm-awac-question question-code=\"AC8\"></mm-awac-question></mm-awac-section></div></div>");$templateCache.put('$/angular/views/municipality/help_actions_fr.html', "<h1>Calculateur commune de l'AWAC: aide sur les actions de réduction</h1>");$templateCache.put('$/angular/views/municipality/help_form_fr.html', "<h1>Calculateur commune de l'AWAC: aide générale</h1><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras arcu nisi, eleifend sed facilisis a, vestibulum vulputate arcu. Curabitur vestibulum blandit feugiat. Mauris molestie magna nisi, et aliquet libero vehicula vitae. Cras tincidunt pellentesque felis, sed tincidunt velit mattis id. Duis id purus massa. Nullam non turpis at augue efficitur cursus. Ut blandit id justo et malesuada. Duis vulputate orci vel tincidunt tincidunt. In hac habitasse platea dictumst. Ut neque augue, lobortis et feugiat a, fringilla id purus. Curabitur eu turpis turpis. Integer elementum lobortis lobortis. Aliquam sodales in nibh id lacinia. Duis fringilla ante est, eget ornare nisi sodales in.</p><p>In a eros vitae risus placerat tristique. Donec faucibus turpis turpis, sit amet bibendum purus ultrices a. Suspendisse vel urna vestibulum, pretium sem sit amet, fringilla nulla. Praesent orci ex, pellentesque ut nisl nec, maximus commodo dui. In tincidunt dui magna, eget euismod ipsum blandit lacinia. Aenean convallis risus non velit rutrum, vulputate fermentum quam dignissim. Phasellus rhoncus lacus ut elementum lobortis. Pellentesque neque mi, facilisis eu varius nec, venenatis in enim. Cras sollicitudin diam quis porttitor tincidunt.</p><p>In ullamcorper, metus sit amet laoreet eleifend, arcu turpis bibendum mauris, non hendrerit dolor augue sed ligula. Quisque dolor diam, blandit faucibus enim non, malesuada aliquam odio. Suspendisse vitae lacus id est aliquam interdum. Fusce porttitor posuere ligula tincidunt posuere. Vivamus iaculis cursus hendrerit. Duis id est consequat, molestie augue at, hendrerit mauris. Proin odio leo, scelerisque quis placerat a, congue non purus. Proin mauris dui, lobortis a ultricies a, iaculis a neque. Curabitur posuere auctor dui. Nunc volutpat odio nibh, quis dapibus tellus dignissim eget. Cras nisl mi, iaculis non tellus ac, semper tincidunt lorem.</p><p>Proin venenatis vitae lectus eget pulvinar. Suspendisse sed lacus ac quam lacinia porta sed ut diam. Maecenas id risus in magna suscipit tincidunt vestibulum sit amet dolor. In in urna urna. Maecenas sagittis eu ligula at commodo. Praesent interdum placerat erat eu porttitor. Nam efficitur vehicula laoreet. Integer id urna turpis.</p><p>Donec faucibus vehicula urna, et sodales diam vestibulum ac. Donec pharetra pulvinar libero. Nam id massa eleifend leo eleifend pretium vel vitae est. Vivamus ut accumsan est. Ut elementum, dolor sed cursus ullamcorper, quam enim pharetra nunc, ut tincidunt est ligula id urna. Pellentesque auctor odio non luctus dapibus. Aliquam a neque id sem consequat imperdiet in nec turpis. Nunc a massa dui. Quisque iaculis sodales sem, sit amet dictum nibh mattis quis. Vivamus tempus maximus tempus. Donec metus eros, iaculis et turpis eleifend, scelerisque viverra quam. Curabitur ut nibh vel leo mattis rutrum. Sed ullamcorper luctus venenatis.</p><img src=\"/assets/images/help/diamond.png\"><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras arcu nisi, eleifend sed facilisis a, vestibulum vulputate arcu. Curabitur vestibulum blandit feugiat. Mauris molestie magna nisi, et aliquet libero vehicula vitae. Cras tincidunt pellentesque felis, sed tincidunt velit mattis id. Duis id purus massa. Nullam non turpis at augue efficitur cursus. Ut blandit id justo et malesuada. Duis vulputate orci vel tincidunt tincidunt. In hac habitasse platea dictumst. Ut neque augue, lobortis et feugiat a, fringilla id purus. Curabitur eu turpis turpis. Integer elementum lobortis lobortis. Aliquam sodales in nibh id lacinia. Duis fringilla ante est, eget ornare nisi sodales in.</p><a target=\"blank\" href=\"http://www.perdu.com\">Open me in a new window</a><p>In a eros vitae risus placerat tristique. Donec faucibus turpis turpis, sit amet bibendum purus ultrices a. Suspendisse vel urna vestibulum, pretium sem sit amet, fringilla nulla. Praesent orci ex, pellentesque ut nisl nec, maximus commodo dui. In tincidunt dui magna, eget euismod ipsum blandit lacinia. Aenean convallis risus non velit rutrum, vulputate fermentum quam dignissim. Phasellus rhoncus lacus ut elementum lobortis. Pellentesque neque mi, facilisis eu varius nec, venenatis in enim. Cras sollicitudin diam quis porttitor tincidunt.</p><p>In ullamcorper, metus sit amet laoreet eleifend, arcu turpis bibendum mauris, non hendrerit dolor augue sed ligula. Quisque dolor diam, blandit faucibus enim non, malesuada aliquam odio. Suspendisse vitae lacus id est aliquam interdum. Fusce porttitor posuere ligula tincidunt posuere. Vivamus iaculis cursus hendrerit. Duis id est consequat, molestie augue at, hendrerit mauris. Proin odio leo, scelerisque quis placerat a, congue non purus. Proin mauris dui, lobortis a ultricies a, iaculis a neque. Curabitur posuere auctor dui. Nunc volutpat odio nibh, quis dapibus tellus dignissim eget. Cras nisl mi, iaculis non tellus ac, semper tincidunt lorem.</p><p>Proin venenatis vitae lectus eget pulvinar. Suspendisse sed lacus ac quam lacinia porta sed ut diam. Maecenas id risus in magna suscipit tincidunt vestibulum sit amet dolor. In in urna urna. Maecenas sagittis eu ligula at commodo. Praesent interdum placerat erat eu porttitor. Nam efficitur vehicula laoreet. Integer id urna turpis.</p><p>Donec faucibus vehicula urna, et sodales diam vestibulum ac. Donec pharetra pulvinar libero. Nam id massa eleifend leo eleifend pretium vel vitae est. Vivamus ut accumsan est. Ut elementum, dolor sed cursus ullamcorper, quam enim pharetra nunc, ut tincidunt est ligula id urna. Pellentesque auctor odio non luctus dapibus. Aliquam a neque id sem consequat imperdiet in nec turpis. Nunc a massa dui. Quisque iaculis sodales sem, sit amet dictum nibh mattis quis. Vivamus tempus maximus tempus. Donec metus eros, iaculis et turpis eleifend, scelerisque viverra quam. Curabitur ut nibh vel leo mattis rutrum. Sed ullamcorper luctus venenatis.</p>");$templateCache.put('$/angular/views/municipality/TAB_C3.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><!--mobilite AC60--><mm-awac-section title-code=\"AC60\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_AC60' | translate\"></div></div><mm-awac-question question-code=\"AC61\" ng-optional=\"true\"></mm-awac-question><!--transport routier AC62--><mm-awac-sub-title question-code=\"AC62\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"AC400\"></mm-awac-sub-sub-title><div><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(1,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,1)\"></i><div class=\"status {{getStatusClassForTab(1,1)}}\"></div><span ng-bind-html=\"'AC402' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question question-code=\"AC403\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"AC404\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"AC405\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(1,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,2)\"></i><div class=\"status {{getStatusClassForTab(1,2)}}\"></div><span ng-bind-html=\"'AC406' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"AC407\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC407\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC407')\"><mm-awac-question question-code=\"AC408\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"AC409\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"AC410\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"AC411\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC407\"></mm-awac-repetition-add-button></div></div></tab><tab class=\"tab-color-yellow\" active=\"getTab(1,3).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,3)\"></i><div class=\"status {{getStatusClassForTab(1,3)}}\"></div><span ng-bind-html=\"'AC412' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"AC413\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC413\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC413')\"><mm-awac-question question-code=\"AC414\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"AC415\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"AC416\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"AC417\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC413\"></mm-awac-repetition-add-button></div></div></tab></tabset></div></div></div><mm-awac-sub-sub-title question-code=\"AC500\"></mm-awac-sub-sub-title><div><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(2,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(2,1)\"></i><div class=\"status {{getStatusClassForTab(2,1)}}\"></div><span ng-bind-html=\"'AC502' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question question-code=\"AC503\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"AC504\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"AC505\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(2,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(2,2)\"></i><div class=\"status {{getStatusClassForTab(2,2)}}\"></div><span ng-bind-html=\"'AC506' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"AC507\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC507\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC507')\"><mm-awac-question question-code=\"AC508\" ng-tab-set=\"2\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"AC509\" ng-tab-set=\"2\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"AC510\" ng-tab-set=\"2\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"AC511\" ng-tab-set=\"2\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC507\"></mm-awac-repetition-add-button></div></div></tab><tab class=\"tab-color-yellow\" active=\"getTab(2,3).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(2,3)\"></i><div class=\"status {{getStatusClassForTab(2,3)}}\"></div><span ng-bind-html=\"'AC512' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"AC513\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC513\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC513')\"><mm-awac-question question-code=\"AC514\" ng-tab-set=\"2\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"AC515\" ng-tab-set=\"2\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"AC516\" ng-tab-set=\"2\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"AC517\" ng-tab-set=\"2\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC513\"></mm-awac-repetition-add-button></div></div></tab></tabset></div></div></div><mm-awac-sub-sub-title question-code=\"AC600\"></mm-awac-sub-sub-title><div><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(3,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(3,1)\"></i><div class=\"status {{getStatusClassForTab(3,1)}}\"></div><span ng-bind-html=\"'AC602' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question question-code=\"AC603\" ng-tab-set=\"3\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"AC604\" ng-tab-set=\"3\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"AC605\" ng-tab-set=\"3\" ng-tab=\"1\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(3,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(3,2)\"></i><div class=\"status {{getStatusClassForTab(3,2)}}\"></div><span ng-bind-html=\"'AC606' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"AC607\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC607\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC607')\"><mm-awac-question question-code=\"AC608\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"AC609\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"AC610\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"AC611\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC607\"></mm-awac-repetition-add-button></div></div></tab><tab class=\"tab-color-yellow\" active=\"getTab(3,3).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(3,3)\"></i><div class=\"status {{getStatusClassForTab(3,3)}}\"></div><span ng-bind-html=\"'AC612' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"AC613\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC613\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC613')\"><mm-awac-question question-code=\"AC614\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"AC615\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"AC616\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"AC617\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC613\"></mm-awac-repetition-add-button></div></div></tab></tabset></div></div></div><!--transport public AC92--><mm-awac-sub-title question-code=\"AC92\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"AC93\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"AC94\"></mm-awac-question><mm-awac-question question-code=\"AC95\"></mm-awac-question><mm-awac-question question-code=\"AC96\"></mm-awac-question><mm-awac-question question-code=\"AC97\"></mm-awac-question><!--déplacements professionels AC98--><mm-awac-sub-title question-code=\"AC98\"></mm-awac-sub-title><mm-awac-question question-code=\"AC99\"></mm-awac-question><mm-awac-question question-code=\"AC100\"></mm-awac-question><mm-awac-question question-code=\"AC101\"></mm-awac-question><mm-awac-question question-code=\"AC102\"></mm-awac-question><mm-awac-question question-code=\"AC103\"></mm-awac-question><mm-awac-question question-code=\"AC104\"></mm-awac-question><mm-awac-question question-code=\"AC105\"></mm-awac-question><!--déplacement avion AC106--><mm-awac-sub-title question-code=\"AC106\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"AC107\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC107\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC107')\"><mm-awac-question question-code=\"AC108\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC109\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC110\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC111\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC112\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC113\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC107\"></mm-awac-repetition-add-button></mm-awac-section></div></div>");$templateCache.put('$/angular/views/municipality/help_site_manager_fr.html', "<h1>Calculateur commune de l'AWAC: aide sur la gestion des sites<p>NE DEVRAIT PAS EXISTER !!!</p></h1>");$templateCache.put('$/angular/views/municipality/TAB_C5.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><!--achat bien et service--><mm-awac-section title-code=\"AC130\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_AC130' | translate\"></div></div><mm-awac-question question-code=\"AC131\" ng-optional=\"true\"></mm-awac-question><mm-awac-repetition-name question-code=\"AC132\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC132\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC132')\"><mm-awac-question question-code=\"AC133\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"(getAnswer('AC133',itLevel1).value | stringToFloat) &lt; 18   || getAnswer('AC133',itLevel1).value == '23'\" question-code=\"AC134\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC133',itLevel1).value == '20' || getAnswer('AC133',itLevel1).value == '21'|| getAnswer('AC133',itLevel1).value == '22'\" question-code=\"AC135\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC133',itLevel1).value == '18' || getAnswer('AC133',itLevel1).value == '19'\" question-code=\"AC136\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC132\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><!--investissement AC137--><mm-awac-section title-code=\"AC137\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_AC137' | translate\"></div></div><mm-awac-question question-code=\"AC138\" ng-optional=\"true\"></mm-awac-question><mm-awac-repetition-name question-code=\"AC139\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC139\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC139')\"><mm-awac-question question-code=\"AC140\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC141\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC142\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC143\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC139\"></mm-awac-repetition-add-button></mm-awac-section></div></div>");$templateCache.put('$/angular/views/municipality/TAB_C4.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><!--achat bien et service--><mm-awac-section title-code=\"AC114\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_AC114' | translate\"></div></div><mm-awac-question question-code=\"AC115\" ng-optional=\"true\"></mm-awac-question><mm-awac-repetition-name question-code=\"AC116\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC116\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC116')\"><mm-awac-question question-code=\"AC117\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC118\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_61'\" question-code=\"AC119\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_62'\" question-code=\"AC120\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_63'\" question-code=\"AC121\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_64'\" question-code=\"AC122\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_65'\" question-code=\"AC123\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_66'\" question-code=\"AC124\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_67'\" question-code=\"AC125\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_68'\" question-code=\"AC126\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_61' || getAnswer('AC118',itLevel1).value == 'AT_62' || getAnswer('AC118',itLevel1).value == 'AT_63' || getAnswer('AC118',itLevel1).value == 'AT_64'\" question-code=\"AC127\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_61' || getAnswer('AC118',itLevel1).value == 'AT_62' || getAnswer('AC118',itLevel1).value == 'AT_63' || getAnswer('AC118',itLevel1).value == 'AT_64' || getAnswer('AC118',itLevel1).value == 'AT_65' || getAnswer('AC118',itLevel1).value == 'AT_66' || getAnswer('AC118',itLevel1).value == 'AT_67'\" question-code=\"AC128\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_68'\" question-code=\"AC129\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC116\"></mm-awac-repetition-add-button></mm-awac-section></div></div>");$templateCache.put('$/angular/views/login.html', "<div class=\"loginBackground\"><div class=\"router-bar\"><div class=\"awac_logo\"></div><h1 ng-bind-html=\"'TITLE_ENTERPRISE' | translate\" ng-show=\"$root.instanceName == 'enterprise'\"></h1><h1 ng-bind-html=\"'TITLE_MUNICIPALITY' | translate\" ng-show=\"$root.instanceName == 'municipality'\"></h1></div><div class=\"loginFrame\" ng-enter=\"enterEvent()\"><select style=\"float:right\" ng-options=\"l.value as l.label for l in $root.languages\" ng-model=\"$root.language\"></select><tabset><tab class=\"tab-color-lightgreen\" active=\"tabActive[0]\"><tab-heading><span ng-bind-html=\"'LOGIN_CONNECTION' | translate\"></span></tab-heading><div><div class=\"field_form\"><form id=\"loginForm\" action=\"\" method=\"post\"><mm-awac-modal-field-text ng-info=\"loginInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text></form></div><div ng-hide=\"isLoading === true\"><button class=\"button btn btn-primary\" ng-disabled=\"!connectionFieldValid()\" ng-click=\"send({anonymous:false})\" ng-bind-html=\"'LOGIN_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"connectionFieldValid()\" ng-click=\"send({anonymous:true})\" ng-bind-html=\"'LOGIN_ANONYMOUS_BUTTON' | translate\" type=\"button\" ng-show=\"$root.instanceName == 'littleEmitter' || $root.instanceName == 'household'\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading === true\"></div></tab><tab class=\"tab-color-lightgreen\" active=\"tabActive[1]\" ng-show=\"true\"><tab-heading><span ng-bind-html=\"'LOGIN_FORGOT_PASSWORD' | translate\"></span></tab-heading><div><div class=\"forgot_password_success_message\" ng-show=\"forgotEmailSuccessMessage!=null\">{{forgotEmailSuccessMessage}}</div><div ng-hide=\"forgotEmailSuccessMessage!=null\"><div ng-bind-html=\"'LOGIN_FORGOT_PASSWORD_DESC' | translate\"></div><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"forgotPasswordInfo\"></mm-awac-modal-field-text></div><div ng-hide=\"isLoading === true\"><button class=\"button btn btn-primary\" ng-disabled=\"!forgotPasswordFieldValid()\" ng-click=\"sendForgotPassword()\" ng-bind-html=\"'SUBMIT' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading === true\"></div></div></tab><tab class=\"tab-color-lightgreen\" active=\"tabActive[2]\" ng-show=\"$root.instanceName != 'verification'\"><tab-heading><span ng-bind-html=\"'LOGIN_REGISTRATION' | translate\"></span></tab-heading><div class=\"inject-registration-form\"></div></tab></tabset></div></div>");$templateCache.put('$/angular/views/verification/verification.html', "<div class=\"verification\"><div class=\"menu-verification\"><div class=\"list-request\"><table class=\"admin-table-import-bad table\" ng-table=\"tableParams\"><tr ng-click=\"$parent.selectRequest(request)\" ng-class=\"{'selected': $parent.requestSelected.id == request.id}\" ng-repeat=\"request in $data\"><td data-title=\"'ORGANIZATION' | translateText\">{{request.organizationCustomer.name}}</td><td sortable=\"'organizationCustomer.interfaceName'\" data-title=\"'CALCULATOR_TYPE'| translateText\">{{request.organizationCustomer.interfaceName}}</td><td sortable=\"'scope.name'\" data-title=\"'SITE_PRODUCT'| translateText\">{{request.scope.name}}</td><td sortable=\"'period.label'\" data-title=\"'SITE_MANAGER_EVENT_PERIOD'| translateText\">{{request.period.label}}</td><td sortable=\"'contact.lastName'\" data-title=\"'CONTACT'| translateText\">{{request.contact.firstName}} {{request.contact.lastName}} {{request.contactPhoneNumber}}</td><td data-title=\"'COMMENT'| translateText\">{{request.comment}}</td><td sortable=\"'status'\" data-title=\"'STATUS'| translateText\">{{request.status | translateText}}</td></tr></table></div></div><div class=\"data_menu\" ng-hide=\"requestSelected === null\"><div class=\"data_date_compare\"><button class=\"button verification\" ng-click=\"consultEvent()\" ng-bind-html=\"'CONSULT_ORGANIZATION_EVENT' | translate\" type=\"button\"></button></div><div class=\"big_separator\"></div><div class=\"data_date_compare\"><div ng-bind-html=\"'SURVEY_INTERFACE_COMPARE_TO' | translate\"></div><select ng-options=\"p.key as p.label for p in periodsForComparison\" ng-model=\"$root.periodToCompare\"></select></div><div class=\"big_separator\"></div><div class=\"data_date_compare\"><button class=\"button verification\" ng-disabled=\"verificationFinalization.finalized != true\" ng-click=\"isDisabled || finalizeVerification()\" ng-bind-html=\"'FINALIZE_VERIFICATION_BUTTON' | translate\" type=\"button\"></button></div></div><div class=\"injectFormMenu\"></div><div class=\"injectForm\"></div></div>");$templateCache.put('$/angular/views/verification/help_submit_fr.html', "<h1>Calculateur commune de l'AWAC (vérification): aide sur la soumission</h1>");$templateCache.put('$/angular/views/verification/help_verification_fr.html', "<h1>Calculateur commune de l'AWAC (vérification): aide sur la vérificaiton</h1>");$templateCache.put('$/angular/views/verification/archive.html', "<div class=\"verification_manage\"><div class=\"my-share-table\" loading-container=\"tableParams.settings().$loading\"><div class=\"verification\"><table class=\"admin-table-import-bad table\" ng-table=\"tableParams\"><tr ng-repeat=\"request in $data\"><td data-title=\"'ORGANIZATION' | translateText\">{{request.organizationCustomer.name}}</td><td sortable=\"'organizationCustomer.interfaceName'\" data-title=\"'CALCULATOR_TYPE'| translateText\">{{request.organizationCustomer.interfaceName}}</td><td sortable=\"'scope.name'\" data-title=\"'SITE_PRODUCT'| translateText\">{{request.scope.name}}</td><td sortable=\"'period.label'\" data-title=\"'SITE_MANAGER_EVENT_PERIOD'| translateText\">{{request.period.label}}</td><td sortable=\"'contact.lastName'\" data-title=\"'CONTACT'| translateText\">{{request.contact.firstName}} {{request.contact.lastName}} {{request.contactPhoneNumber}}</td><td data-title=\"'COMMENT'| translateText\">{{request.comment}}</td><td data-title=\"'FINALIZED'| translateText\"><span ng-bind-html=\"'YES' | translate\" ng-show=\"request.status === 'VERIFICATION_STATUS_VERIFIED'\"></span><span ng-bind-html=\"'NO' | translate\" ng-hide=\"request.status === 'VERIFICATION_STATUS_VERIFIED'\"></span></td><td sortable=\"'request.type'\" data-title=\"'VERIFICATION_ASSIGNED_USERS'| translateText\"><div ng-repeat=\"user in request.verifierList\">{{user.firstName}} {{user.lastName}}</div></td><td data-title=\"'MODAL_DOCUMENT_MANAGER_ACTION'| translateText\"><button class=\"button verification\" ng-click=\"downloadReport(request)\" ng-bind-html=\"'DOWNLOAD_VERIFICATION_REPORT' | translate\" type=\"button\" ng-show=\"request.status === 'VERIFICATION_STATUS_VERIFIED'\"></button></td></tr></table></div></div></div>");$templateCache.put('$/angular/views/verification/help_archive_fr.html', "<h1>Calculateur commune de l'AWAC (vérification): aide sur l'archive</h1>");$templateCache.put('$/angular/views/verification/help_manage_fr.html', "<h1>Calculateur commune de l'AWAC (vérification): aide sur la gestion</h1>");$templateCache.put('$/angular/views/verification/manage.html', "<div class=\"verification_manage\"><div class=\"my-share-table\" loading-container=\"tableParams.settings().$loading\"><div class=\"verification\"><table class=\"admin-table-import-bad table\" ng-table=\"tableParams\"><tr ng-repeat=\"request in $data\"><td data-title=\"'ORGANIZATION' | translateText\">{{request.organizationCustomer.name}}</td><td sortable=\"'organizationCustomer.interfaceName'\" data-title=\"'CALCULATOR_TYPE'| translateText\">{{request.organizationCustomer.interfaceName}}</td><td sortable=\"'scope.name'\" data-title=\"'SITE_PRODUCT'| translateText\">{{request.scope.name}}</td><td sortable=\"'period.label'\" data-title=\"'SITE_MANAGER_EVENT_PERIOD'| translateText\">{{request.period.label}}</td><td sortable=\"'contact.lastName'\" data-title=\"'CONTACT'| translateText\">{{request.contact.firstName}} {{request.contact.lastName}} {{request.contactPhoneNumber}}</td><td data-title=\"'COMMENT'| translateText\">{{request.comment}}</td><td sortable=\"'status'\" data-title=\"'STATUS'| translateText\">{{request.status | translateText}}</td><td sortable=\"'request.type'\" data-title=\"'VERIFICATION_ASSIGNED_USERS'| translateText\"><div ng-repeat=\"user in request.verifierList\">{{user.firstName}} {{user.lastName}}</div></td><td data-title=\"'MODAL_DOCUMENT_MANAGER_ACTION'| translateText\"><div ng-show=\"loadingRequest[request.id] === true\"><img src=\"/assets/images/modal-loading.gif\"></div><div ng-hide=\"loadingRequest[request.id] === true\"><button class=\"button verification\" ng-click=\"changeStatus(request,'VERIFICATION_STATUS_WAIT_CUSTOMER_CONFIRMATION')\" ng-bind-html=\"'VERIFICATION_REQUEST_ACCEPT' | translate\" type=\"button\" ng-show=\"request.status === 'VERIFICATION_STATUS_WAIT_VERIFIER_CONFIRMATION'\"></button><button class=\"button verification\" ng-click=\"changeStatus(request,'VERIFICATION_STATUS_REJECTED')\" ng-bind-html=\"'VERIFICATION_REQUEST_REJECT' | translate\" type=\"button\" ng-show=\"request.status === 'VERIFICATION_STATUS_WAIT_VERIFIER_CONFIRMATION'\"></button><button class=\"button verification\" ng-click=\"assignRequest(request)\" ng-bind-html=\"'VERIFICATION_REQUEST_ASSIGN' | translate\" type=\"button\" ng-show=\"request.status === 'VERIFICATION_STATUS_WAIT_ASSIGNATION' || request.status === 'VERIFICATION_STATUS_VERIFICATION'\"></button></div></td></tr></table></div><div><div class=\"desc_block\" ng-bind-html=\"'ADD_REQUEST_WITH_KEY_DESC'| translate\"></div><span ng-bind-html=\"'ADD_REQUEST_WITH_KEY' | translate\"></span><input ng-model=\"keyToInject\"><button class=\"button\" ng-disabled=\"keyToInject==null || keyToInject.length == 0\" ng-click=\"isDisabled || addKey()\" ng-bind-html=\"'SUBMIT' | translate\" type=\"button\"></button></div></div></div>");$templateCache.put('$/angular/views/verification/submit.html', "<div class=\"verification\"><div class=\"menu-verification\"><div class=\"list-request\"><table class=\"admin-table-import-bad table\" ng-table=\"tableParams\"><tr ng-click=\"$parent.selectRequest(request)\" ng-class=\"{'selected': $parent.requestSelected.id == request.id}\" ng-repeat=\"request in $data\"><td data-title=\"'ORGANIZATION' | translateText\">{{request.organizationCustomer.name}}</td><td sortable=\"'organizationCustomer.interfaceName'\" data-title=\"'CALCULATOR_TYPE'| translateText\">{{request.organizationCustomer.interfaceName}}</td><td sortable=\"'scope.name'\" data-title=\"'SITE_PRODUCT'| translateText\">{{request.scope.name}}</td><td sortable=\"'period.label'\" data-title=\"'SITE_MANAGER_EVENT_PERIOD'| translateText\">{{request.period.label}}</td><td sortable=\"'contact.lastName'\" data-title=\"'CONTACT'| translateText\">{{request.contact.firstName}} {{request.contact.lastName}} {{request.contactPhoneNumber}}</td><td data-title=\"'COMMENT'| translateText\">{{request.comment}}</td><td sortable=\"'status'\" data-title=\"'STATUS'| translateText\">{{request.status | translateText}}</td></tr></table></div></div><div class=\"data_menu\" ng-hide=\"requestSelected === null\"><div class=\"data_date_compare\"><button class=\"button verification\" ng-click=\"consultEvent()\" ng-bind-html=\"'CONSULT_ORGANIZATION_EVENT' | translate\" type=\"button\"></button></div><div class=\"big_separator\"></div><div class=\"data_date_compare\"><div ng-bind-html=\"'SURVEY_INTERFACE_COMPARE_TO' | translate\"></div><select ng-options=\"p.key as p.label for p in periodsForComparison\" ng-model=\"$root.periodToCompare\"></select></div><div class=\"big_separator\"></div><div class=\"data_date_compare\"><button class=\"button verification\" ng-click=\"isDisabled || validVerificationFinalization(true)\" ng-bind-html=\"'VERIFICATION_CUSTOMER_CONFIRMATION_WINDOW_ACCEPT' | translate\" type=\"button\"></button><button class=\"button verification\" ng-click=\"isDisabled || validVerificationFinalization(false)\" ng-bind-html=\"'VERIFICATION_CUSTOMER_CONFIRMATION_WINDOW_REJECT' | translate\" type=\"button\"></button><button class=\"button verification\" ng-click=\"isDisabled || downloadFile()\" ng-bind-html=\"'DOWNLOAD_VERIFICATION_REPORT' | translate\" type=\"button\" ng-show=\"selectedRequest.status == 'VERIFICATION_STATUS_WAIT_VERIFICATION_CONFIRMATION_SUCCESS' \"></button><button class=\"button verification\" ng-click=\"isDisabled || consultFinalComment()\" ng-bind-html=\"'VERIFICATION_FINALIZATION_CONSULT_COMMENT' | translate\" type=\"button\" ng-show=\"selectedRequest.status == 'VERIFICATION_STATUS_WAIT_VERIFICATION_CONFIRMATION_REJECT' \"></button></div></div><div class=\"injectFormMenu\"></div><div class=\"injectForm\"></div></div>");$templateCache.put('$/angular/views/change_password.html', "<div class=\"loginBackground\"><div class=\"loginFrame\" ng-enter=\"send()\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"oldPasswordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"newPasswordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"newPasswordInfo_confirm\"></mm-awac-modal-field-text></div><p style=\"background-color:#ff0000;color:#ffffff;padding:15px\" ng-show=\"errorMessage.length &gt; 0\">{{errorMessage}}</p><button ng-click=\"send()\" ng-bind-html=\"'CHANGE_PASSWORD_BUTTON' | translate\" class=\"btn btn-primary\" type=\"button\"></button></div></div>");$templateCache.put('$/angular/views/enterprise/confidentiality_fr.html', "<h1>Calculateur entreprise de l'AWAC: gestion de la confidentialité de vos données</h1>");$templateCache.put('$/angular/views/enterprise/help_organization_manager_fr.html', "<h1>Calculateur entreprise de l'AWAC: aide sur la gestion de l'organisation</h1>");$templateCache.put('$/angular/views/enterprise/help_results_fr.html', "<h1>Calculateur entreprise de l'AWAC: aide sur la présentation des résultats</h1>");$templateCache.put('$/angular/views/enterprise/help_user_data_fr.html', "<h1>Calculateur entreprise de l'AWAC: aide sur le profil individuel</h1>");$templateCache.put('$/angular/views/enterprise/help_user_manager_fr.html', "<h1>Calculateur entreprise de l'AWAC: aide sur la gestion des utilisateurs</h1>");$templateCache.put('$/angular/views/enterprise/help_actions_fr.html', "<h1>Calculateur entreprise de l'AWAC: aide sur les actions de réduction</h1>");$templateCache.put('$/angular/views/enterprise/TAB6.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"A229\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A229' | translate\"></div></div><mm-awac-question question-code=\"A230\" ng-optional=\"true\"></mm-awac-question><mm-awac-repetition-name question-code=\"A231\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A231\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A231')\"><mm-awac-question question-code=\"A232\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A233\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"(getAnswer('A233',itLevel1).value | stringToFloat) &lt; 18   || getAnswer('A233',itLevel1).value == '23'\" question-code=\"A234\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A233',itLevel1).value == '20' || getAnswer('A233',itLevel1).value == '21'|| getAnswer('A233',itLevel1).value == '22'\" question-code=\"A235\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A233',itLevel1).value == '18' || getAnswer('A233',itLevel1).value == '19'\" question-code=\"A236\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A231\"></mm-awac-repetition-add-button><mm-awac-sub-sub-title question-code=\"A237\"></mm-awac-sub-sub-title><mm-awac-repetition-name question-code=\"A238\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A238\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A238')\"><mm-awac-question question-code=\"A239\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A240\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A241\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A242\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A238\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A309\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A309' | translate\"></div></div><mm-awac-question question-code=\"A310\" ng-optional=\"true\"></mm-awac-question><mm-awac-repetition-name question-code=\"A311\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A311\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A311')\"><mm-awac-question question-code=\"A312\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A313\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A313\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A313',itLevel1)\"><mm-awac-question question-code=\"A314\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A315\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A313\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1012\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A1012\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A1012',itLevel1)\"><mm-awac-question question-code=\"A1013\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A1014\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A1012\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1015\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A1015\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A1015',itLevel1)\"><mm-awac-question question-code=\"A1016\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A1017\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A1015\"></mm-awac-repetition-add-button><mm-awac-question question-code=\"A316\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A317\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A317\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A317',itLevel1)\"><mm-awac-question question-code=\"A318\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A319\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A317\"></mm-awac-repetition-add-button></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A311\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A320\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A320' | translate\"></div></div><mm-awac-question question-code=\"A321\" ng-optional=\"true\"></mm-awac-question><mm-awac-repetition-name question-code=\"A322\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A322\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A322')\"><mm-awac-question question-code=\"A323\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A324\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A325\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A325\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A325',itLevel1)\"><mm-awac-question question-code=\"A326\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A327\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A325\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1018\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A1018\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A1018',itLevel1)\"><mm-awac-question question-code=\"A1019\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A1020\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A1018\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1021\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A1021\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A1021',itLevel1)\"><mm-awac-question question-code=\"A1022\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A1023\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A1021\"></mm-awac-repetition-add-button><mm-awac-question question-code=\"A328\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A329\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A329\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A329',itLevel1)\"><mm-awac-question question-code=\"A330\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A331\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A329\"></mm-awac-repetition-add-button></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A322\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A332\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A332' | translate\"></div></div><mm-awac-question question-code=\"A333\" ng-optional=\"true\"></mm-awac-question><mm-awac-repetition-name question-code=\"A334\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A334\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A334')\"><mm-awac-question question-code=\"A335\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A336\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A337\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A338\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A334\"></mm-awac-repetition-add-button></mm-awac-section></div></div>");$templateCache.put('$/angular/views/enterprise/help_form_fr.html', "<h1>Calculateur entreprise de l'AWAC: aide générale</h1><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras arcu nisi, eleifend sed facilisis a, vestibulum vulputate arcu. Curabitur vestibulum blandit feugiat. Mauris molestie magna nisi, et aliquet libero vehicula vitae. Cras tincidunt pellentesque felis, sed tincidunt velit mattis id. Duis id purus massa. Nullam non turpis at augue efficitur cursus. Ut blandit id justo et malesuada. Duis vulputate orci vel tincidunt tincidunt. In hac habitasse platea dictumst. Ut neque augue, lobortis et feugiat a, fringilla id purus. Curabitur eu turpis turpis. Integer elementum lobortis lobortis. Aliquam sodales in nibh id lacinia. Duis fringilla ante est, eget ornare nisi sodales in.</p><p>In a eros vitae risus placerat tristique. Donec faucibus turpis turpis, sit amet bibendum purus ultrices a. Suspendisse vel urna vestibulum, pretium sem sit amet, fringilla nulla. Praesent orci ex, pellentesque ut nisl nec, maximus commodo dui. In tincidunt dui magna, eget euismod ipsum blandit lacinia. Aenean convallis risus non velit rutrum, vulputate fermentum quam dignissim. Phasellus rhoncus lacus ut elementum lobortis. Pellentesque neque mi, facilisis eu varius nec, venenatis in enim. Cras sollicitudin diam quis porttitor tincidunt.</p><p>In ullamcorper, metus sit amet laoreet eleifend, arcu turpis bibendum mauris, non hendrerit dolor augue sed ligula. Quisque dolor diam, blandit faucibus enim non, malesuada aliquam odio. Suspendisse vitae lacus id est aliquam interdum. Fusce porttitor posuere ligula tincidunt posuere. Vivamus iaculis cursus hendrerit. Duis id est consequat, molestie augue at, hendrerit mauris. Proin odio leo, scelerisque quis placerat a, congue non purus. Proin mauris dui, lobortis a ultricies a, iaculis a neque. Curabitur posuere auctor dui. Nunc volutpat odio nibh, quis dapibus tellus dignissim eget. Cras nisl mi, iaculis non tellus ac, semper tincidunt lorem.</p><p>Proin venenatis vitae lectus eget pulvinar. Suspendisse sed lacus ac quam lacinia porta sed ut diam. Maecenas id risus in magna suscipit tincidunt vestibulum sit amet dolor. In in urna urna. Maecenas sagittis eu ligula at commodo. Praesent interdum placerat erat eu porttitor. Nam efficitur vehicula laoreet. Integer id urna turpis.</p><p>Donec faucibus vehicula urna, et sodales diam vestibulum ac. Donec pharetra pulvinar libero. Nam id massa eleifend leo eleifend pretium vel vitae est. Vivamus ut accumsan est. Ut elementum, dolor sed cursus ullamcorper, quam enim pharetra nunc, ut tincidunt est ligula id urna. Pellentesque auctor odio non luctus dapibus. Aliquam a neque id sem consequat imperdiet in nec turpis. Nunc a massa dui. Quisque iaculis sodales sem, sit amet dictum nibh mattis quis. Vivamus tempus maximus tempus. Donec metus eros, iaculis et turpis eleifend, scelerisque viverra quam. Curabitur ut nibh vel leo mattis rutrum. Sed ullamcorper luctus venenatis.</p><!--public/images/help--><img src=\"/assets/images/help/diamond.png\"><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras arcu nisi, eleifend sed facilisis a, vestibulum vulputate arcu. Curabitur vestibulum blandit feugiat. Mauris molestie magna nisi, et aliquet libero vehicula vitae. Cras tincidunt pellentesque felis, sed tincidunt velit mattis id. Duis id purus massa. Nullam non turpis at augue efficitur cursus. Ut blandit id justo et malesuada. Duis vulputate orci vel tincidunt tincidunt. In hac habitasse platea dictumst. Ut neque augue, lobortis et feugiat a, fringilla id purus. Curabitur eu turpis turpis. Integer elementum lobortis lobortis. Aliquam sodales in nibh id lacinia. Duis fringilla ante est, eget ornare nisi sodales in.</p><a target=\"blank\" href=\"http://www.perdu.com\">Open me in a new window</a><p>In a eros vitae risus placerat tristique. Donec faucibus turpis turpis, sit amet bibendum purus ultrices a. Suspendisse vel urna vestibulum, pretium sem sit amet, fringilla nulla. Praesent orci ex, pellentesque ut nisl nec, maximus commodo dui. In tincidunt dui magna, eget euismod ipsum blandit lacinia. Aenean convallis risus non velit rutrum, vulputate fermentum quam dignissim. Phasellus rhoncus lacus ut elementum lobortis. Pellentesque neque mi, facilisis eu varius nec, venenatis in enim. Cras sollicitudin diam quis porttitor tincidunt.</p><p>In ullamcorper, metus sit amet laoreet eleifend, arcu turpis bibendum mauris, non hendrerit dolor augue sed ligula. Quisque dolor diam, blandit faucibus enim non, malesuada aliquam odio. Suspendisse vitae lacus id est aliquam interdum. Fusce porttitor posuere ligula tincidunt posuere. Vivamus iaculis cursus hendrerit. Duis id est consequat, molestie augue at, hendrerit mauris. Proin odio leo, scelerisque quis placerat a, congue non purus. Proin mauris dui, lobortis a ultricies a, iaculis a neque. Curabitur posuere auctor dui. Nunc volutpat odio nibh, quis dapibus tellus dignissim eget. Cras nisl mi, iaculis non tellus ac, semper tincidunt lorem.</p><p>Proin venenatis vitae lectus eget pulvinar. Suspendisse sed lacus ac quam lacinia porta sed ut diam. Maecenas id risus in magna suscipit tincidunt vestibulum sit amet dolor. In in urna urna. Maecenas sagittis eu ligula at commodo. Praesent interdum placerat erat eu porttitor. Nam efficitur vehicula laoreet. Integer id urna turpis.</p><p>Donec faucibus vehicula urna, et sodales diam vestibulum ac. Donec pharetra pulvinar libero. Nam id massa eleifend leo eleifend pretium vel vitae est. Vivamus ut accumsan est. Ut elementum, dolor sed cursus ullamcorper, quam enim pharetra nunc, ut tincidunt est ligula id urna. Pellentesque auctor odio non luctus dapibus. Aliquam a neque id sem consequat imperdiet in nec turpis. Nunc a massa dui. Quisque iaculis sodales sem, sit amet dictum nibh mattis quis. Vivamus tempus maximus tempus. Donec metus eros, iaculis et turpis eleifend, scelerisque viverra quam. Curabitur ut nibh vel leo mattis rutrum. Sed ullamcorper luctus venenatis.</p>");$templateCache.put('$/angular/views/enterprise/TAB5.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><!--mm-awac-section(\"Déchets\")--><!--It lacks a proper fild code for \"D2chets alone\" -> TODO : insert into Excel file as an additional line--><mm-awac-section title-code=\"A173\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A173' | translate\"></div></div><mm-awac-question question-code=\"A174\" ng-optional=\"true\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A4999\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A4999' | translate\"></div></div><mm-awac-repetition-name question-code=\"A5000\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A5000\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A5000')\"><mm-awac-question question-code=\"A5001\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A5002\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A5003\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A5000\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A180\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A180' | translate\"></div></div><mm-awac-sub-title question-code=\"A181\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"A182\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A183\"></mm-awac-question><mm-awac-question question-code=\"A184\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A185\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A186\"></mm-awac-question><mm-awac-question question-code=\"A187\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A188\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A189\"></mm-awac-question><mm-awac-question question-code=\"A190\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A191\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A192\"></mm-awac-question><mm-awac-question question-code=\"A193\"></mm-awac-question><mm-awac-sub-title question-code=\"A194\"></mm-awac-sub-title><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(1,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,1)\"></i><div class=\"status {{getStatusClassForTab(1,1)}}\"></div><span ng-bind-html=\"'A197' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question question-code=\"A198\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A199\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A200\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(1,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,2)\"></i><div class=\"status {{getStatusClassForTab(1,2)}}\"></div><span ng-bind-html=\"'A201' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question question-code=\"A202\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A203\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A204\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question></div></div></tab></tabset></div></mm-awac-section></div></div>");$templateCache.put('$/angular/views/enterprise/TAB2.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"A1\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A1' | translate\"></div></div><mm-awac-question question-code=\"A2\"></mm-awac-question><mm-awac-question question-code=\"A3\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A3').value == '1'\" question-code=\"A4\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A3').value == '2' || getAnswer('A3').value == '3'\" question-code=\"A5\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A3').value == '4'\" question-code=\"A6\"></mm-awac-question><mm-awac-question question-code=\"A9\"></mm-awac-question><mm-awac-question question-code=\"A10\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A3').value != '4'\" question-code=\"A11\"></mm-awac-question><mm-awac-question question-code=\"A12\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A13\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A13' | translate\"></div></div><mm-awac-question question-code=\"A14\" ng-optional=\"true\"></mm-awac-question><mm-awac-repetition-name question-code=\"A15\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A15\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A15')\"><mm-awac-question question-code=\"A16\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A17\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A15\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1000\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A1000\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A1000')\"><mm-awac-question question-code=\"A1001\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A1002\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A1000\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1003\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A1003\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A1003')\"><mm-awac-question question-code=\"A1004\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A1005\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A1003\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A20\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A20' | translate\"></div></div><mm-awac-question question-code=\"A21\" ng-optional=\"true\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A22\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A23\"></mm-awac-question><mm-awac-question question-code=\"A24\"></mm-awac-question><mm-awac-repetition-name question-code=\"A25\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A25\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A25')\"><mm-awac-question question-code=\"A26\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A27\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A28\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A25\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A31\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A31' | translate\"></div></div><mm-awac-question question-code=\"A32\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A32').value == '1'\" question-code=\"A33\" ng-optional=\"true\"></mm-awac-question><mm-awac-block ng-condition=\"getAnswer('A32').value == '1'\"><mm-awac-repetition-name question-code=\"A34\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A34\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A34')\"><mm-awac-question question-code=\"A35\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A36\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A34\"></mm-awac-repetition-add-button></mm-awac-block></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A37\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A37' | translate\"></div></div><mm-awac-question question-code=\"A38\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A38').value == '1'\" question-code=\"A39\" ng-optional=\"true\"></mm-awac-question></mm-awac-section><mm-awac-block ng-condition=\"getAnswer('A38').value == '1'\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(1,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,1)\"></i><div class=\"status {{getStatusClassForTab(1,1)}}\"></div><span ng-bind-html=\"'A41' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A42\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A42\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A42')\"><mm-awac-question question-code=\"A43\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A44\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A42\"></mm-awac-repetition-add-button></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(1,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,2)\"></i><div class=\"status {{getStatusClassForTab(1,2)}}\"></div><span ng-bind-html=\"'A45' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question question-code=\"A46\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question></div></div></tab><tab class=\"tab-color-yellow\" active=\"getTab(1,3).active\" ng-show=\"getAnswer('A5').value == '1' || getAnswer('A5').value == '2'\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,3)\"></i><div class=\"status {{getStatusClassForTab(1,3)}}\"></div><span ng-bind-html=\"'A47' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><mm-awac-block class=\"element_table\" ng-condition=\"getAnswer('A5').value == '1' || getAnswer('A5').value == '2'\"><mm-awac-question question-code=\"A48\" ng-tab-set=\"1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"A49\" ng-tab-set=\"1\" ng-tab=\"3\"></mm-awac-question></mm-awac-block></div></tab></tabset></div></div></mm-awac-block><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A6000\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A6000' | translate\"></div></div><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(2,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(2,1)\"></i><div class=\"status {{getStatusClassForTab(2,1)}}\"></div><span ng-bind-html=\"'A6002' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question question-code=\"A6003\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A6004\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A6005\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(2,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(2,2)\"></i><div class=\"status {{getStatusClassForTab(2,2)}}\"></div><span ng-bind-html=\"'A6006' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question question-code=\"A6007\" ng-tab-set=\"2\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A6008\" ng-tab-set=\"2\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A6009\" ng-tab-set=\"2\" ng-tab=\"2\"></mm-awac-question></div></div></tab></tabset></div></mm-awac-section></div></div>");$templateCache.put('$/angular/views/enterprise/TAB4.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"A205\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A20' | translate\"></div></div><mm-awac-question question-code=\"A206\" ng-optional=\"true\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A208\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A20' | translate\"></div></div><mm-awac-repetition-name question-code=\"A209\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A209\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A209')\"><mm-awac-question question-code=\"A210\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A211\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_61'\" question-code=\"A212\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_62'\" question-code=\"A213\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_63'\" question-code=\"A214\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_64'\" question-code=\"A215\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_65'\" question-code=\"A216\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_66'\" question-code=\"A217\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_67'\" question-code=\"A218\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_68'\" question-code=\"A219\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_61' || getAnswer('A211',itLevel1).value == 'AT_62' || getAnswer('A211',itLevel1).value == 'AT_63' || getAnswer('A211',itLevel1).value == 'AT_64'\" question-code=\"A220\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value != 'AT_68'\" question-code=\"A221\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_68'\" question-code=\"A222\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A209\"></mm-awac-repetition-add-button><mm-awac-sub-title question-code=\"A223\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"A224\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A224\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A224')\"><mm-awac-question question-code=\"A225\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A226\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A227\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A228\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A224\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A128\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A128' | translate\"></div></div><mm-awac-question question-code=\"A129\" ng-optional=\"true\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A130\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A130' | translate\"></div></div><mm-awac-sub-title question-code=\"A131\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"A132\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A133\"></mm-awac-question><mm-awac-question question-code=\"A134\"></mm-awac-question><mm-awac-question question-code=\"A135\"></mm-awac-question><mm-awac-question question-code=\"A136\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A136').value == '1'\" question-code=\"A137\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A136').value == '1'\" question-code=\"A138\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A138').value == '1'\" question-code=\"A139\"></mm-awac-question><mm-awac-question ng-aggregation=\"0.484\" ng-condition=\"getAnswer('A138').value == '0'\" question-code=\"A500\"></mm-awac-question><mm-awac-sub-title question-code=\"A140\"></mm-awac-sub-title><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(1,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,1)\"></i><div class=\"status {{getStatusClassForTab(1,1)}}\"></div><span ng-bind-html=\"'A141' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_text\" ng-bind-html=\"'DETAIL_UPSTREAM_KM' | translate\"></div><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A142\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A142\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A142')\"><mm-awac-question question-code=\"A143\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A145\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A146\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A147\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A148\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A149\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A150\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A151\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A152\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A153\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A154\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A155\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question ng-aggregation=\"(getAnswer('A147',itLevel1).value | nullToZero)+( getAnswer('A148',itLevel1).value | nullToZero)+(getAnswer('A149',itLevel1).value | nullToZero)+(getAnswer('A150',itLevel1).value| nullToZero)+(getAnswer('A151',itLevel1).value| nullToZero)+(getAnswer('A152',itLevel1).value| nullToZero)+(getAnswer('A153',itLevel1).value| nullToZero)+(getAnswer('A154',itLevel1).value| nullToZero)+(getAnswer('A155',itLevel1).value| nullToZero)\" question-code=\"A156\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A142\"></mm-awac-repetition-add-button></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(1,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,2)\"></i><div class=\"status {{getStatusClassForTab(1,2)}}\"></div><span ng-bind-html=\"'A157' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question question-code=\"A158\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A159\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-aggregation=\"5000\" ng-condition=\"getAnswer('A159').value == '3'\" question-code=\"A160\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-aggregation=\"2500\" ng-condition=\"getAnswer('A159').value == '2'\" question-code=\"A161\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-aggregation=\"200\" ng-condition=\"getAnswer('A159').value == '1'\" question-code=\"A162\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question></div></div></tab></tabset></div></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A163\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A163' | translate\"></div></div><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'INTRO_UPSTREAM_DISTRIBUTION' | translate\"></div></div><mm-awac-repetition-name question-code=\"A164\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A164\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A164')\"><mm-awac-question question-code=\"A165\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A166\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A166\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A166',itLevel1)\"><mm-awac-question question-code=\"A167\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A168\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A166\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1006\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A1006\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A1006',itLevel1)\"><mm-awac-question question-code=\"A1007\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A1008\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A1006\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1009\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A1009\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A1009',itLevel1)\"><mm-awac-question question-code=\"A1010\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A1011\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A1009\"></mm-awac-repetition-add-button><mm-awac-question question-code=\"A169\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A170\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A170\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A170',itLevel1)\"><mm-awac-question question-code=\"A171\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A172\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A170\"></mm-awac-repetition-add-button></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A164\"></mm-awac-repetition-add-button></mm-awac-section></div></div>");$templateCache.put('$/angular/views/enterprise/help_site_manager_fr.html', "<h1>Calculateur entreprise de l'AWAC: aide sur la gestion des sites</h1>");$templateCache.put('$/angular/views/enterprise/TAB7.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"A243\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A243' | translate\"></div></div><mm-awac-repetition-name question-code=\"A244\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" ng-question-set-code=\"'A244'\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A244')\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A245'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A246'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A247'\"></mm-awac-question><mm-awac-block><!--Transport--><mm-awac-sub-title question-code=\"A250\"></mm-awac-sub-title><mm-awac-question question-code=\"A251\" ng-optional=\"true\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A252\"></mm-awac-sub-sub-title><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(1,1,itLevel1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,1,itLevel1)\"></i><div class=\"status {{getStatusClassForTab(1,1,itLevel1)}}\"></div><span ng-bind-html=\"'A253' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question question-code=\"A254\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A255\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A256\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A257\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A258\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A259\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A260\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A261\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A262\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A263\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A264\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question ng-aggregation=\"(getAnswer('A256',itLevel1).value | nullToZero)+( getAnswer('A257',itLevel1).value | nullToZero)+(getAnswer('A258',itLevel1).value | nullToZero)+(getAnswer('A259',itLevel1).value| nullToZero)+(getAnswer('A260',itLevel1).value| nullToZero)+(getAnswer('A261',itLevel1).value| nullToZero)+(getAnswer('A262',itLevel1).value| nullToZero)+(getAnswer('A263',itLevel1).value| nullToZero)+(getAnswer('A264',itLevel1).value| nullToZero)\" question-code=\"A265\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(1,2,itLevel1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,2,itLevel1)\"></i><div class=\"status {{getStatusClassForTab(1,2,itLevel1)}}\"></div><span ng-bind-html=\"'A266' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question question-code=\"A267\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A268\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-aggregation=\"5000\" ng-condition=\"getAnswer('A268',itLevel1).value == '3'\" question-code=\"A269\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-aggregation=\"2500\" ng-condition=\"getAnswer('A268',itLevel1).value == '2'\" question-code=\"A270\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-aggregation=\"200\" ng-condition=\"getAnswer('A268',itLevel1).value == '1'\" question-code=\"A271\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question></div></div></tab></tabset></div></mm-awac-block><mm-awac-block><!--Distribution--><mm-awac-sub-title question-code=\"A272\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"A273\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" ng-question-set-code=\"'A273'\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A273',itLevel1)\"><mm-awac-question question-code=\"A274\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-repetition-name question-code=\"A275\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel3\" question-set-code=\"A275\" ng-repetition-map=\"itLevel2\" ng-repeat=\"itLevel3 in getRepetitionMapByQuestionSet('A275',itLevel2)\"><mm-awac-question question-code=\"A276\" ng-repetition-map=\"itLevel3\"></mm-awac-question><mm-awac-question question-code=\"A277\" ng-repetition-map=\"itLevel3\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel2\" question-set-code=\"A275\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1024\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel3\" question-set-code=\"A1024\" ng-repetition-map=\"itLevel2\" ng-repeat=\"itLevel3 in getRepetitionMapByQuestionSet('A1024',itLevel2)\"><mm-awac-question question-code=\"A1025\" ng-repetition-map=\"itLevel3\"></mm-awac-question><mm-awac-question question-code=\"A1026\" ng-repetition-map=\"itLevel3\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel2\" question-set-code=\"A1024\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1027\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel3\" question-set-code=\"A1027\" ng-repetition-map=\"itLevel2\" ng-repeat=\"itLevel3 in getRepetitionMapByQuestionSet('A1027',itLevel2)\"><mm-awac-question question-code=\"A1028\" ng-repetition-map=\"itLevel3\"></mm-awac-question><mm-awac-question question-code=\"A1029\" ng-repetition-map=\"itLevel3\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel2\" question-set-code=\"A1027\"></mm-awac-repetition-add-button><mm-awac-question question-code=\"A278\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-repetition-name question-code=\"A279\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel3\" question-set-code=\"A279\" ng-repetition-map=\"itLevel2\" ng-repeat=\"itLevel3 in getRepetitionMapByQuestionSet('A279',itLevel2)\"><mm-awac-question question-code=\"A280\" ng-repetition-map=\"itLevel3\"></mm-awac-question><mm-awac-question question-code=\"A281\" ng-repetition-map=\"itLevel3\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel2\" question-set-code=\"A279\"></mm-awac-repetition-add-button></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A273\"></mm-awac-repetition-add-button></mm-awac-block><mm-awac-block><!--sous-question final ou intermediaire--><mm-awac-sub-title question-code=\"A8000\"></mm-awac-sub-title><mm-awac-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A248'\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A248',itLevel1).value == '2'\" ng-repetition-map=\"itLevel1\" ng-question-code=\"'A249'\"></mm-awac-question></mm-awac-block><mm-awac-block ng-condition=\"getAnswer('A249',itLevel1).value == '1'\"><!--Traitement--><mm-awac-sub-title question-code=\"A282\"></mm-awac-sub-title><mm-awac-question question-code=\"A283\" ng-optional=\"true\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A284\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A284\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A284',itLevel1)\"><mm-awac-question question-code=\"A285\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A286\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A284\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1030\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A1030\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A1030',itLevel1)\"><mm-awac-question question-code=\"A1031\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A1032\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A1030\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1033\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A1033\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A1033',itLevel1)\"><mm-awac-question question-code=\"A1034\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A1035\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A1033\"></mm-awac-repetition-add-button><mm-awac-question question-code=\"A287\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A288\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A288\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A288',itLevel1)\"><mm-awac-question question-code=\"A289\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A290\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A288\"></mm-awac-repetition-add-button></mm-awac-block><mm-awac-block ng-condition=\"getAnswer('A248',itLevel1).value == '1' || getAnswer('A249',itLevel1).value == '1'\"><!--Utilisation--><mm-awac-sub-title question-code=\"A291\"></mm-awac-sub-title><mm-awac-question question-code=\"A292\" ng-optional=\"true\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A293\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A294\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A295\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A296\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A297\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A297\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A297',itLevel1)\"><mm-awac-question question-code=\"A298\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A299\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A297\"></mm-awac-repetition-add-button></mm-awac-block><mm-awac-block ng-condition=\"getAnswer('A248',itLevel1).value == '1' || getAnswer('A249',itLevel1).value == '1'\"><!--Fin de vie--><mm-awac-sub-title question-code=\"A300\"></mm-awac-sub-title><mm-awac-question question-code=\"A301\" ng-optional=\"true\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A302\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A5010\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A5010\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A5010',itLevel1)\"><mm-awac-question question-code=\"A5011\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A5012\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A5013\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A5014\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-iteration=\"itLevel1\" question-set-code=\"A5010\"></mm-awac-repetition-add-button></mm-awac-block></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A244\"></mm-awac-repetition-add-button></mm-awac-section></div></div>");$templateCache.put('$/angular/views/enterprise/TAB3.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"A50\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A50' | translate\"></div></div><mm-awac-question question-code=\"A51\" ng-optional=\"true\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A52\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A52' | translate\"></div></div><mm-awac-sub-title question-code=\"A400\"></mm-awac-sub-title><div><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(1,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,1)\"></i><div class=\"status {{getStatusClassForTab(1,1)}}\"></div><span ng-bind-html=\"'A402' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question question-code=\"A403\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A404\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A405\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(1,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,2)\"></i><div class=\"status {{getStatusClassForTab(1,2)}}\"></div><span ng-bind-html=\"'A406' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A407\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A407\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A407')\"><mm-awac-question question-code=\"A408\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A409\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A410\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A411\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A407\"></mm-awac-repetition-add-button></div></div></tab><tab class=\"tab-color-yellow\" active=\"getTab(1,3).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,3)\"></i><div class=\"status {{getStatusClassForTab(1,3)}}\"></div><span ng-bind-html=\"'A412' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A413\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A413\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A413')\"><mm-awac-question question-code=\"A414\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"A415\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"A416\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"A417\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A413\"></mm-awac-repetition-add-button></div></div></tab></tabset></div></div></div><mm-awac-sub-title question-code=\"A518\"></mm-awac-sub-title><div><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(2,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(2,1)\"></i><div class=\"status {{getStatusClassForTab(2,1)}}\"></div><span ng-bind-html=\"'A502' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question question-code=\"A503\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A504\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A505\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(2,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(2,2)\"></i><div class=\"status {{getStatusClassForTab(2,2)}}\"></div><span ng-bind-html=\"'A506' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A507\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A507\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A507')\"><mm-awac-question question-code=\"A508\" ng-tab-set=\"2\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A509\" ng-tab-set=\"2\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A510\" ng-tab-set=\"2\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A511\" ng-tab-set=\"2\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A507\"></mm-awac-repetition-add-button></div></div></tab><tab class=\"tab-color-yellow\" active=\"getTab(2,3).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(2,3)\"></i><div class=\"status {{getStatusClassForTab(2,3)}}\"></div><span ng-bind-html=\"'A512' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A513\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A513\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A513')\"><mm-awac-question question-code=\"A514\" ng-tab-set=\"2\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"A515\" ng-tab-set=\"2\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"A516\" ng-tab-set=\"2\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"A517\" ng-tab-set=\"2\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A513\"></mm-awac-repetition-add-button></div></div></tab></tabset></div></div></div><mm-awac-sub-title question-code=\"A600\"></mm-awac-sub-title><div><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(3,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(3,1)\"></i><div class=\"status {{getStatusClassForTab(3,1)}}\"></div><span ng-bind-html=\"'A602' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question question-code=\"A603\" ng-tab-set=\"3\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A604\" ng-tab-set=\"3\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A605\" ng-tab-set=\"3\" ng-tab=\"1\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(3,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(3,2)\"></i><div class=\"status {{getStatusClassForTab(3,2)}}\"></div><span ng-bind-html=\"'A606' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A607\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A607\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A607')\"><mm-awac-question question-code=\"A608\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A609\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A610\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A611\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A607\"></mm-awac-repetition-add-button></div></div></tab><tab class=\"tab-color-yellow\" active=\"getTab(3,3).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(3,3)\"></i><div class=\"status {{getStatusClassForTab(3,3)}}\"></div><span ng-bind-html=\"'A612' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A613\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A613\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A613')\"><mm-awac-question question-code=\"A614\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"A615\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"A616\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"A617\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A613\"></mm-awac-repetition-add-button></div></div></tab></tabset></div></div></div></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A93\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A93' | translate\"></div></div><mm-awac-sub-title question-code=\"A93\"></mm-awac-sub-title><mm-awac-question question-code=\"A95\"></mm-awac-question><mm-awac-question question-code=\"A96\"></mm-awac-question><mm-awac-question question-code=\"A97\"></mm-awac-question><mm-awac-question question-code=\"A98\"></mm-awac-question><mm-awac-question question-code=\"A99\"></mm-awac-question><mm-awac-question question-code=\"A100\"></mm-awac-question><mm-awac-question question-code=\"A101\"></mm-awac-question><mm-awac-question question-code=\"A102\"></mm-awac-question><mm-awac-question question-code=\"A103\"></mm-awac-question><mm-awac-question question-code=\"A104\"></mm-awac-question><mm-awac-question question-code=\"A105\"></mm-awac-question><mm-awac-question question-code=\"A106\"></mm-awac-question><mm-awac-question question-code=\"A107\"></mm-awac-question><mm-awac-question question-code=\"A108\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A113\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A113' | translate\"></div></div><mm-awac-sub-title question-code=\"A113\"></mm-awac-sub-title><div><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(5,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(5,1)\"></i><div class=\"status {{getStatusClassForTab(5,1)}}\"></div><span ng-bind-html=\"'A114' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A115\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A115\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A115')\"><mm-awac-question question-code=\"A116\" ng-tab-set=\"5\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A117\" ng-tab-set=\"5\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A118\" ng-tab-set=\"5\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A119\" ng-tab-set=\"5\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A120\" ng-tab-set=\"5\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A115\"></mm-awac-repetition-add-button></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(5,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(5,2)\"></i><div class=\"status {{getStatusClassForTab(5,2)}}\"></div><span ng-bind-html=\"'A121' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question question-code=\"A122\" ng-tab-set=\"5\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A123\" ng-tab-set=\"5\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A123',itLevel1).value == '0'\" question-code=\"A124\" ng-tab-set=\"5\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-aggregation=\"2500\" ng-condition=\"getAnswer('A124',itLevel1).value == '1'\" question-code=\"A125\" ng-tab-set=\"5\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-aggregation=\"5000\" ng-condition=\"getAnswer('A124',itLevel1).value == '0'\" question-code=\"A126\" ng-tab-set=\"5\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A123',itLevel1).value == '1'\" question-code=\"A127\" ng-tab-set=\"5\" ng-tab=\"2\"></mm-awac-question></div></div></tab></tabset></div></div></div></mm-awac-section></div></div>");$templateCache.put('$/angular/views/admin.html', "<div><h1>Admin</h1><tabset style=\"margin-top:20px\"><tab class=\"tab-color-lightgreen\"><tab-heading style=\"margin-left:25px\"><span ng-bind-html=\"'BAD Importer'\"></span></tab-heading><div style=\"border-top : 1px solid black\"><div class=\"element_table\"><mm-awac-admin-bad-importer></mm-awac-admin-bad-importer></div></div></tab><tab class=\"tab-color-lightgreen\"><tab-heading><span ng-bind-html=\"'Other .... '\"></span></tab-heading><div style=\"border-top : 1px solid black\"><div class=\"element_table\"><div>something</div></div></div></tab></tabset></div>");$templateCache.put('$/angular/views/user_data.html', "<div><div class=\"menu_close\" ng-click=\"toForm()\"></div><h1 ng-bind-html=\"'USER_DATA_BUTTON' | translate\"></h1><div class=\"user_data\"><div class=\"desc\" ng-bind-html=\"'ANONYMOUS_PROFILE_DESC' | translateText\" ng-if=\"isAnonymousUser()\"></div><div class=\"field_form\" ng-if=\"isAnonymousUser()\"><mm-awac-modal-field-text ng-info=\"anonymous.loginInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"anonymous.passwordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"anonymous.lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"anonymous.firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"anonymous.emailInfo\"></mm-awac-modal-field-text><br><div style=\"display:table-row\"><div style=\"display:table-cell\"></div><div style=\"display:table-cell\"></div><div style=\"display:table-cell\"><div style=\"text-align: right\" ng-hide=\"isLoading\"><button ng-disabled=\"!anonymousFieldValid()\" ng-click=\"sendAnonymous()()\" ng-bind-html=\"'SAVE_BUTTON' | translate\" class=\"btn btn-primary\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div><div style=\"display:table\" class=\"field_form\" ng-if=\"!isAnonymousUser()\"></div></div><div class=\"field_form\" ng-if=\"!isAnonymousUser()\"><mm-awac-modal-field-text ng-info=\"identifierInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"><button class=\"edit_icon\" title=\"{{'UPDATE_PASSWORD_BUTTON' | translateText}}\" ng-click=\"changePassword()\" type=\"button\"><span class=\"glyphicon glyphicon-pencil\"></span></button></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"emailInfo\"><button class=\"edit_icon\" title=\"{{'UPDATE_EMAIL_BUTTON' | translateText}}\" ng-click=\"changeEmail()\" type=\"button\"><span class=\"glyphicon glyphicon-pencil\"></span></button></mm-awac-modal-field-text><div class=\"field_row\"><div class=\"field_cell\"></div><div class=\"field_cell field_wide\"><div style=\"text-align: right\" ng-hide=\"isLoading\"><button ng-disabled=\"!allFieldValid()\" ng-click=\"send()\" ng-bind-html=\"'SAVE_BUTTON' | translate\" class=\"btn btn-primary\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div></div>");$templateCache.put('$/angular/views/admin/driver.html', "<div class=\"verification_manage\"><table class=\"actions-table\"><tr><th>name</th><th>valeurs</th></tr><tr ng-repeat=\"driver in drivers\"><td>{{driver.name}}</td><td><div ng-repeat=\"value in driver.driverValues\"><div style=\"width:50%;display:inline-block\">A partir de<select ng-options=\"p.key as p.label for p in getPeriod(driver,value)\" ng-model=\"value.fromPeriodKey\"></select></div><div style=\"display:inline-block\">Value<input ng-model=\"value.defaultValue\"></div><div style=\"display:inline-block\"><button ng-click=\"remove(driver,value.tempId)\">supprimer</button></div></div><button ng-click=\"addValue(driver)\">Ajouter une value</button></td></tr></table><button ng-click=\"save()\">Sauver les modifications</button><button ng-click=\"orderDriverValues()\">Trier les valeurs par date</button></div>");$templateCache.put('$/angular/views/admin/manage_advices.html', "<div class=\"actions\"><span><h1>{{'REDUCTION_ACTIONS' | translateText }}</h1></span><br><table class=\"actions-table\"><thead><tr><th>{{ \"REDUCTION_ACTION_TABLE_DETAILS\" | translateText }}</th><th>{{ \"REDUCTION_ACTION_TABLE_ACTIONS\" | translateText }}</th><th>{{ \"REDUCTION_ACTION_TABLE_TYPE\" | translateText }}</th><th>{{ \"REDUCTION_ACTION_TABLE_SCOPE\" | translateText }}</th><th>{{ \"REDUCTION_ACTION_TABLE_COMPLETION\" | translateText }}</th></tr></thead><tbody><tr ng-repeat-start=\"a in actions\"><td class=\"details\"><mm-plus-minus-toggle-button ng-model=\"a.opened\"></mm-plus-minus-toggle-button></td><td class=\"title\"><span class=\"text\">{{a.title}}</span></td><td class=\"type\"><span class=\"icon action_type_icon\" ng-class=\"{reducing: a.typeKey == '1', better_measure: a.typeKey == '2'}\"></span><span>&nbsp;&nbsp;</span><span class=\"text\">{{ getTypeLabel(a.typeKey) }}</span></td><td class=\"scope\"><span class=\"icon scope_type_icon\" ng-class=\"{org: a.scopeTypeKey == '1', site: a.scopeTypeKey == '2'}\"></span><span>&nbsp;&nbsp;</span><span class=\"text\">{{ getScopeName(a.scopeTypeKey, a.scopeId) }}</span></td><td class=\"realization\"><span class=\"icon action_done_icon\" ng-show=\"a.statusKey == '2'\"></span><span>&nbsp;&nbsp;</span><span class=\"text\">{{ (a.statusKey == \"2\") ? (a.completionDate | date : \"yyyy\") : getStatusLabel(a.statusKey) }}</span></td></tr><tr ng-repeat-end ng-show=\"a.opened\" class=\"details\"><td colspan=\"2\"><table><tr><td>{{ \"REDUCTION_ACTION_WEBSITE_FIELD_TITLE\" | translateText }}:</td><td><a target=\"blank\" ng-if=\"a.webSite != null\" href=\"{{a.webSite}}\">{{ a.webSite }}</a></td></tr><tr><td>{{ \"REDUCTION_ACTION_PHYSICAL_MEASURE_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\">{{ a.physicalMeasure || \"&nbsp;\" }}</div></td></tr><tr ng-if=\"a.typeKey == '1'\"><td>{{ \"REDUCTION_ACTION_GHG_BENEFIT_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box\">{{ a.ghgBenefit || \"&nbsp;\" }}</div><div class=\"box transparent\">{{ getGwpUnitSymbol(a.ghgBenefitUnitKey) }}</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_FINANCIAL_BENEFIT_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box\">{{ a.financialBenefit || \"&nbsp;\" }}</div><div class=\"box transparent\">euros</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_INVESTMENT_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box\">{{ a.investmentCost || \"&nbsp;\" }}</div><div class=\"box transparent\">euros</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_EXPECTED_PAYBACK_TIME_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box\">{{ a.expectedPaybackTime || \"&nbsp;\" }}</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_DUE_DATE_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box\">{{ a.dueDate | date: 'dd/MM/yyyy' }}</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_RESPONSIBLE_PERSON_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\">{{ a.responsiblePerson || \"&nbsp;\" }}</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_COMMENT_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\" style=\"height: 150px;\">{{ a.comment || \"&nbsp;\" }}</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_ATTACHMENTS_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\"><!--span(ng-show=\"a.files.length == 0\") &nbsp;--><div ng-repeat=\"f in a.files\"><a ng-href=\"/awac/file/download/{{ f.id }}\">{{ f.name }}</a></div></div></td></tr></table></td><td colspan=\"4\" style=\"vertical-align: bottom; text-align: right\"><button class=\"button\" ng-click=\"edit(a)\" type=\"button\"><span class=\"fa fa-pencil-square-o\"></span><span>&nbsp;</span><span ng-bind-html=\"'REDUCING_ACTION_BUTTON_EDIT' | translate\"></span></button></td></tr></tbody></table><br><button class=\"button\" ng-click=\"create()\" type=\"button\"><span class=\"fa fa-plus-circle\"></span><span>&nbsp;</span><span ng-bind-html=\"'REDUCING_ACTION_BUTTON_CREATE' | translate\"></span></button><button class=\"button\" ng-click=\"exportToXls()\" type=\"button\"><span class=\"fa fa-file-excel-o\"></span><span>&nbsp;</span><span ng-bind-html=\"'REDUCING_ACTION_BUTTON_EXPORT_TO_XLS' | translate\"></span></button></div>");$templateCache.put('$/angular/views/user_manager.html', "<div><div class=\"menu_close\" ng-click=\"toForm()\"></div><h1 ng-bind-html=\"'USER_MANAGER_TITLE' | translate\"></h1><div class=\"element\"><button class=\"button add\" ng-click=\"inviteUser()\" ng-bind-html=\"'USER_MANAGER_INVIT_USER' | translate\" type=\"button\" ng-show=\"true\"></button><table class=\"user_table\"><tr class=\"user_table_header\"><td ng-bind-html=\"'NAME' | translate\"></td><td ng-bind-html=\"'USER_MANAGER_ADMINISTRATOR' | translate\"></td><td ng-bind-html=\"'USER_MANAGER_MAIN_VERIFIER' | translate\" ng-show=\"$root.instanceName==='verification'\"></td><td ng-bind-html=\"'USER_MANAGER_ACTIF' | translate\"></td></tr><tr ng-class=\"{user_deleted : user.isActive === false}\" ng-repeat=\"user in getUserList()\"><td>{{user.firstName}} {{user.lastName}} ({{user.email}})</td><td><input ng-disabled=\"getMyself().isAdmin === false || getMyself().email === user.email || user.isActive == false\" ng-click=\"isAdminUser(user)\" ng-model=\"user.isAdmin\" type=\"checkbox\" ng-hide=\"isLoading['admin'][user.email]=== true\"><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading['admin'][user.email] === true\"></td><td ng-show=\"$root.instanceName==='verification'\"><input ng-disabled=\"getMyself().isAdmin === false || getMyself().email === user.email || user.isActive == false\" ng-click=\"isMainVerifier(user)\" ng-model=\"user.isMainVerifier\" type=\"checkbox\" ng-hide=\"isLoading['admin'][user.email]=== true\"><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading['isMainVerifier'][user.email] === true\"></td><td ng-class=\"{is_admin : getMyself().isAdmin === true &amp;&amp; getMyself().email !== user.email}\"><div class=\"button_delete\" ng-click=\"activeUser(user)\" ng-hide=\"isLoading['isActive'][user.email]=== true\"></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading['isActive'][user.email] === true\"></td></tr></table></div></div>");$templateCache.put('$/angular/views/site_manager.html', "<div><div class=\"menu_close\" ng-click=\"toForm()\"></div><h1 ng-bind-html=\"'SITE_MANAGER_BUTTON' | translateText\"></h1><div class=\"site_manager\"><h4 ng-bind-html=\"'SITE_MANAGER_SITE_LIST_TITLE' | translateText\"></h4><div class=\"desc\" ng-bind-html=\"'SITE_MANAGER_SITE_LIST_DESC' | translateText\"></div><table class=\"site_table\"><tr class=\"site_table_header\"><td ng-bind-html=\"'SITE_MANAGER_EDIT_SITE_BUTTON' | translateText\"></td><td ng-bind-html=\"'NAME' | translateText\"></td><td ng-bind-html=\"'DESCRIPTION' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_NACE_CODE' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_ORGANIZATIONAL_STRUCTURE' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_PERCENT_OWNED' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_ADD_USERS_BUTTON' | translateText\"></td><td><select ng-options=\"p.key as p.label for p in $root.periods\" ng-model=\"assignPeriod\"></select></td></tr><tr ng-repeat=\"site in getSiteList()\"><td><button title=\"{{'SITE_MANAGER_EDIT_SITE_BUTTON' | translateText}}\" ng-click=\"editOrCreateSite(site)\" class=\"edit_icon glyphicon glyphicon-pencil\" type=\"button\"></button></td><td>{{site.name}}</td><td>{{site.description}}</td><td>{{site.naceCode}}</td><td>{{site.organizationalStructure | translateText }}</td><td>{{site.percentOwned}} %</td><td><button title=\"{{'SITE_MANAGER_ADD_USERS_BUTTON' | translateText}}\" ng-click=\"addUsers(site)\" class=\"edit_icon glyphicon glyphicon-pencil\" type=\"button\"></button></td><td><input ng-click=\"assignPeriodToSite(site)\" ng-model=\"isPeriodChecked[site.id]\" type=\"checkbox\" ng-hide=\"isLoading[site.id]=== true\"><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading[site.id]=== true\"></td></tr></table><button class=\"button add\" ng-click=\"editOrCreateSite()\" ng-bind-html=\"'SITE_MANAGER_ADD_SITE_BUTTON' | translateText\" type=\"button\"></button></div></div>");$templateCache.put('$/angular/views/results.html', "<div class=\"results pdf-able\"><h1><span ng-bind-html=\"'RESULTS' | translate\"></span></h1><table class=\"wide\"><tr><td class=\"top-aligned\" ng-hide=\"$root.instanceName == 'municipality'\"><div class=\"sites-panel\"><div class=\"sites-panel-title\"><span ng-bind-html=\"'SITES_LIST' | translate\"></span></div><!--<div class=\"sites-panel-all-items\"><table><tr><td><span ng-bind-html=\"'ALL_SITES_SELECTED' | translate\"></span></td><td><input type=\"checkbox\"></td></tr></table></div>--><div class=\"sites-panel-items\"><div class=\"sites-panel-item\"><table><tr ng-repeat=\"site in mySites\"><td>{{ site.name }}</td><td><input ng-model=\"site.selected\" type=\"checkbox\"></td></tr></table></div></div><div class=\"sites-panel-title\"><span ng-bind-html=\"'SURVEY_INTERFACE_VERIFICATION' | translate\"></span></div><div class=\"sites-panel-item\" ng-show=\"verificationRequests.length&gt; 0 \"><table><tr ng-repeat=\"verificationRequest in verificationRequests\"><td>{{ verificationRequest.scope.name }}</td><td><button class=\"button\" ng-click=\"downloadVerificationReport(verificationRequest)\" type=\"button\" ng-show=\"getVerificationRequestStatus() === 'VERIFICATION_STATUS_VERIFIED'\"><span ng-bind-html=\"'DOWNLOAD_VERIFICATION_REPORT' | translate\"></span></button></td></tr></table></div></div></td><td class=\"top-aligned horizontally-padded wide\"><div ng-show=\"o != null &amp;&amp; o != undefined\"><div ng-show=\"o.reportDTOs.R_1.rightPeriod == null\"><span ng-bind-html=\"'ACCOMPANIMENT_WORD_ENTERPRISE' | translateWithVars:[(leftTotalEmissions | numberToI18N)]\" ng-show=\"$root.instanceName == 'enterprise'\"></span><span ng-bind-html=\"'ACCOMPANIMENT_WORD_MUNICIPALITY' | translateWithVars:[(leftTotalEmissions | numberToI18N)]\" ng-show=\"$root.instanceName == 'municipality'\"></span></div><div ng-hide=\"o.reportDTOs.R_1.rightPeriod == null\"><span ng-bind-html=\"'ACCOMPANIMENT_COMPARISION_WORD_ENTERPRISE' | translateWithVars:[(leftTotalEmissions | numberToI18N),o.reportDTOs.R_1.leftPeriod,(rightTotalEmissions | numberToI18N),o.reportDTOs.R_1.rightPeriod]\" ng-show=\"$root.instanceName == 'enterprise'\"></span><span ng-bind-html=\"'ACCOMPANIMENT_COMPARISION_WORD_MUNICIPALITY' | translateWithVars:[(leftTotalEmissions | numberToI18N),o.reportDTOs.R_1.leftPeriod,(rightTotalEmissions | numberToI18N),o.reportDTOs.R_1.rightPeriod]\" ng-show=\"$root.instanceName == 'municipality'\"></span></div><br><br><div ng-show=\"siteSelectionIsEmpty()\"><div ng-bind-html=\"'SELECT_AT_LEAST_ONE_SITE' | translate\"></div></div><div ng-hide=\"siteSelectionIsEmpty()\"><div ng-show=\"current_tab == 1\"><h2><span ng-bind-html=\"'VALUES_BY_CATEGORY' | translate\"></span></h2><br><center><div class=\"diagram\" style=\"height: 10cm\" ng-bind-html=\"o.svgHistograms.R_1 | trustAsHtml\"></div></center><br><center><div><mm-awac-result-legend ng-model=\"o.reportDTOs.R_1\" mode=\"numbers\"></mm-awac-result-legend></div></center></div><div ng-show=\"current_tab == 2\"><h2><span ng-bind-html=\"'IMPACTS_PARTITION' | translate\"></span></h2><br><table><tr><td colspan=\"3\"><h3><span ng-bind-html=\"'SCOPE_1' | translate\"></span><span> : {{ leftTotalScope1 | numberToI18N }} tCO2e</span></h3></td></tr><tr><td><div class=\"diagram\" style=\"display:inline-block; max-width: 5cm; max-height: 5cm;\" ng-bind-html=\"o.leftSvgDonuts.R_2 | trustAsHtml\"></div><span>&nbsp;</span><div class=\"diagram\" style=\"display:inline-block; max-width: 5cm; max-height: 5cm;\" ng-bind-html=\"o.rightSvgDonuts.R_2 | trustAsHtml\"></div></td><td style=\"width: 2em\"></td><td><mm-awac-result-legend ng-model=\"o.reportDTOs.R_2\"></mm-awac-result-legend></td></tr><tr><td colspan=\"3\"><h3><span ng-bind-html=\"'SCOPE_2' | translate\"></span><span> : {{ leftTotalScope2 | numberToI18N }} tCO2e</span></h3></td></tr><tr><td><div class=\"diagram\" style=\"display:inline-block; max-width: 5cm; max-height: 5cm;\" ng-bind-html=\"o.leftSvgDonuts.R_3 | trustAsHtml\"></div><span>&nbsp;</span><div class=\"diagram\" style=\"display:inline-block; max-width: 5cm; max-height: 5cm;\" ng-bind-html=\"o.rightSvgDonuts.R_3 | trustAsHtml\"></div></td><td style=\"width: 2em\"></td><td><mm-awac-result-legend ng-model=\"o.reportDTOs.R_3\"></mm-awac-result-legend></td></tr><tr><td colspan=\"3\"><h3><span ng-bind-html=\"'SCOPE_3' | translate\"></span><span> : {{ leftTotalScope3 | numberToI18N }} tCO2e</span></h3></td></tr><tr><td><div class=\"diagram\" style=\"display:inline-block; max-width: 5cm; max-height: 5cm;\" ng-bind-html=\"o.leftSvgDonuts.R_4 | trustAsHtml\"></div><span>&nbsp;</span><div class=\"diagram\" style=\"display:inline-block; max-width: 5cm; max-height: 5cm;\" ng-bind-html=\"o.rightSvgDonuts.R_4 | trustAsHtml\"></div></td><td style=\"width: 2em\"></td><td><mm-awac-result-legend ng-model=\"o.reportDTOs.R_4\"></mm-awac-result-legend></td></tr></table><br><br></div><div ng-show=\"current_tab == 3\"><h2><span ng-bind-html=\"'KIVIAT_DIAGRAM' | translate\"></span></h2><br><center><div class=\"diagram\" style=\"display:inline-block; max-width: 15cm; max-height: 15cm;\" ng-bind-html=\"o.svgWebs.R_1 | trustAsHtml\"></div></center><br><center><mm-awac-result-legend ng-model=\"o.reportDTOs.R_1\" mode=\"numbers\"></mm-awac-result-legend></center></div><div ng-show=\"current_tab == 4\"><h2><span ng-bind-html=\"'NUMBERS' | translate\"></span></h2><mm-awac-result-table ng-model=\"o.reportDTOs.R_1\"></mm-awac-result-table></div><div ng-show=\"current_tab == 5\"><h2><span ng-bind-html=\"'COMPARISION_WITH_CONSTANT_EMISSION_FACTORS' | translate\"></span></h2></div><div ng-show=\"current_tab == 6\"><h2><span ng-bind-html=\"'CALCULUS_EXPLANATION' | translate\"></span></h2><br><p ng-show=\"o.reportDTOs.R_1.rightPeriod != null\"><span ng-bind-html=\"'RESULTS_EXPLANATION_ONLY_AVAILABLE_FOR_SINGLE_PERIOD' | translate\"></span></p><p ng-repeat=\"e in o.logEntries\" ng-hide=\"o.reportDTOs.R_1.rightPeriod != null\"><span ng-show=\"e.__type == 'eu.factorx.awac.dto.awac.get.ReportLogContributionEntryDTO'\"><span ng-bind-html=\"'RESULTS_EXPLANATION_CONTRIB_PART1' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.biActivityCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.biActivitySubCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.adActivityType | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.adActivitySource | translate\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_CONTRIB_PART2' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.adValue | numberToI18NRoundedOrFullIfLessThanOne\"></span><span>&#32;</span><span ng-bind-html=\"e.adUnit\"></span><span>&#32;</span><br><span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_CONTRIB_PART3' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.biIndicatorCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.adActivityType | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.adActivitySource | translate\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_CONTRIB_PART4' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.fValue | numberToI18NRoundedOrFullIfLessThanOne\"></span><span>&#32;</span><span ng-bind-html=\"e.fUnitOut\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_CONTRIB_PART5' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.fUnitIn\"></span><br><span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_CONTRIB_PART6' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.value | numberToI18N\"></span><span>&#32;</span><span ng-bind-html=\"e.biUnit\"></span></span><span style=\"color: #a33\" ng-show=\"e.__type == 'eu.factorx.awac.dto.awac.get.ReportLogNoSuitableFactorEntryDTO'\"><span ng-bind-html=\"'RESULTS_EXPLANATION_NOFACTOR_PART1' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.biActivityCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.biActivitySubCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.adActivityType | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.adActivitySource | translate\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_NOFACTOR_PART2' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.adValue | numberToI18NRoundedOrFullIfLessThanOne\"></span><span>&#32;</span><span ng-bind-html=\"e.adUnit\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_NOFACTOR_PART3' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.biIndicatorCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.adActivityType | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.adActivitySource | translate\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_NOFACTOR_PART4' | translate\"></span></span><span style=\"color: #33a\" ng-show=\"e.__type == 'eu.factorx.awac.dto.awac.get.LowerRankInGroupDTO'\"><span ng-bind-html=\"'RESULTS_EXPLANATION_LOWER_RANK_PART1' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.activityCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.activitySubCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.activityType | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.activitySource | translate\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_LOWER_RANK_PART2' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.value | numberToI18NRoundedOrFullIfLessThanOne\"></span><span>&#32;</span><span ng-bind-html=\"e.unit\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_LOWER_RANK_PART3' | translate\"></span></span><span style=\"color: #a33\" ng-show=\"e.__type == 'eu.factorx.awac.dto.awac.get.NoSuitableIndicatorDTO'\"><span ng-bind-html=\"'RESULTS_EXPLANATION_NOINDICATOR_PART1' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.activityCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.activitySubCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.activityType | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.activitySource | translate\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_NOINDICATOR_PART2' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.value | numberToI18NRoundedOrFullIfLessThanOne\"></span><span>&#32;</span><span ng-bind-html=\"e.unit\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_NOINDICATOR_PART3' | translate\"></span></span></p></div><div ng-show=\"current_tab == 7\"><h2><span ng-bind-html=\"'USED_EMISSION_FACTORS' | translate\"></span></h2></div><div ng-show=\"current_tab == 8\"><h2><span ng-bind-html=\"'CONVENTION_OF_MAYORS' | translate\"></span></h2><br><center><div class=\"diagram\" style=\"height: 10cm\" ng-bind-html=\"o.svgHistograms.R_5 | trustAsHtml\"></div></center><br><center><div><mm-awac-result-legend ng-model=\"o.reportDTOs.R_5\" mode=\"numbers\"></mm-awac-result-legend></div></center></div><br><div class=\"results_disclaimer\"><span class=\"results_disclaimer_text\" ng-bind-html=\"'RESULTS_DISCLAIMER' | translate\"></span></div><br><br><br></div></div></td><td class=\"top-aligned\"><div class=\"align-right\"><button class=\"button\" ng-disabled=\"xlsLoading\" ng-click=\"exportXls()\" type=\"button\"><img src=\"/assets/images/loading_small.gif\" ng-show=\"xlsLoading\"><span ng-bind-html=\"'XLS_EXPORT' | translate\" ng-hide=\"xlsLoading\"></span></button><button class=\"button\" ng-disabled=\"pdfLoading\" ng-click=\"exportPdf()\" type=\"button\"><img src=\"/assets/images/loading_small.gif\" ng-show=\"pdfLoading\"><span ng-bind-html=\"'PDF_EXPORT' | translate\" ng-hide=\"pdfLoading\"></span></button></div><br><div class=\"charts-panel-tabset\"><div class=\"charts-panel-tab\" ng-click=\"current_tab = 1\" ng-class=\"{ active: current_tab == 1 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_bars\" ng-bind-html=\"'VALUES_BY_CATEGORY' | translate\"></div></div><div class=\"charts-panel-tab\" ng-click=\"current_tab = 2\" ng-class=\"{ active: current_tab == 2 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_donut\" ng-bind-html=\"'IMPACTS_PARTITION' | translate\"></div></div><div class=\"charts-panel-tab\" ng-click=\"current_tab = 3\" ng-class=\"{ active: current_tab == 3 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_web\" ng-bind-html=\"'KIVIAT_DIAGRAM' | translate\"></div></div><div class=\"charts-panel-tab\" ng-click=\"current_tab = 4\" ng-class=\"{ active: current_tab == 4 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_numbers\" ng-bind-html=\"'NUMBERS' | translate\"></div></div><div class=\"charts-panel-tab\" mm-not-implemented ng-click=\"\" ng-class=\"{ active: current_tab == 5 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_constant_factors\" ng-bind-html=\"'COMPARISION_WITH_CONSTANT_EMISSION_FACTORS' | translate\"></div></div><div class=\"charts-panel-tab\" ng-click=\"current_tab = 6\" ng-class=\"{ active: current_tab == 6 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_calculus\" ng-bind-html=\"'CALCULUS_EXPLANATION' | translate\"></div></div><div class=\"charts-panel-tab\" ng-click=\"current_tab = 8\" ng-class=\"{ active: current_tab == 8 }\" ng-show=\"$root.instanceName == 'municipality'\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_convention\" ng-bind-html=\"'CONVENTION_OF_MAYORS' | translate\"></div></div><!--<div class=\"charts-panel-tab\" ng-click=\"current_tab = 7\" ng-class=\"{ active: current_tab == 7 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_fe\" ng-bind-html=\"'USED_EMISSION_FACTORS' | translate\"></div></div>--></div></td></tr></table></div>");$templateCache.put('$/angular/views/verification_registration.html', "<div class=\"loginBackground\"><div class=\"router-bar\"><div class=\"awac_logo\"></div></div><div class=\"registrationFrame\" ng-enter=\"enterEvent()\"><select style=\"float:right\" ng-options=\"l.value as l.label for l in $root.languages\" ng-model=\"$root.language\"></select><tabset><tab class=\"tab-color-lightgreen\" active=\"tabActive[0]\"><tab-heading><span ng-bind-html=\"'VERIFICATION_REGISTRATION' | translate\"></span></tab-heading><div><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.emailInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.identifierInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.passwordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.passwordConfirmInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.organizationNameInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"registration()\" ng-bind-html=\"'REGISTRATION_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></tab></tabset></div></div>");$templateCache.put('$/angular/views/user_registration.html', "<div class=\"loginBackground\"><div class=\"router-bar\"><div class=\"awac_logo\"></div></div><div class=\"registrationFrame\" ng-enter=\"enterEvent()\"><select style=\"float:right\" ng-options=\"l.value as l.label for l in $root.languages\" ng-model=\"$root.language\"></select><tabset><tab class=\"tab-color-lightgreen\" active=\"tabActive[0]\"><tab-heading><span ng-bind-html=\"'USER_REGISTRATION' | translate\"></span></tab-heading><div><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.loginInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.passwordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.emailInfo\"></mm-awac-modal-field-text></div><div ng-hide=\"isLoading === true\"><button class=\"button btn btn-primary\" ng-disabled=\"!connectionFieldValid()\" ng-click=\"send()\" ng-bind-html=\"'USER_REGISTER_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading === true\"></div></tab></tabset></div></div>");$templateCache.put('$/angular/views/no_scope.html', "<div><h1 ng-bind-html=\"'NO_SCOPE_TITLE' | translate\"></h1><div class=\"no-scope-message\" ng-bind-html=\"'NO_SCOPE_MESSAGE' | translate\"></div></div>");$templateCache.put('$/angular/templates/mm-awac-admin-bad-importer.html', "<div><button class=\"btn btn-default\" ng-click=\"import()\">Import BAD</button><tabset style=\"margin-top:20px\"><tab class=\"tab-color-lightgreen\"><tab-heading style=\"margin-left:25px\"><span>BAD (Erros :  {{total_bad_error}} )</span></tab-heading><div style=\"border-top : 1px solid black\"><div class=\"element_table\"><table class=\"table\" style=\"width: 200px;\"><tr><td>Total BAd imported</td><td>{{total_bad}}</td></tr><tr><td>Bad with info</td><td>{{total_bad_info}}</td></tr><tr><td>Bad with warning</td><td>{{total_bad_warning}}</td></tr><tr><td>Bad with error</td><td>{{total_bad_error}}</td></tr></table><div class=\"width:100%\" loading-container=\"tableParams.settings().$loading\"><table class=\"table admin-table-import-bad width:100%\" ng-table=\"tableParams\"><tr ng-repeat=\"logLine in $data\"><td style=\"width:12.5%\" sortable=\"'lineNb'\" data-title=\"'Line nb'\">{{logLine.lineNb}}</td><td style=\"width:12.5%\" sortable=\"'name'\" data-title=\"'Code'\">{{logLine.name}}</td><td style=\"width:25%\" sortable=\"'messagesInfoNb'\" data-title=\"'Info'\"><ul><li ng-repeat=\"message in logLine.messages['INFO']\">{{message}}</li></ul></td><td style=\"width:25%\" sortable=\"'messagesWarningNb'\" data-title=\"'Warinig'\"><ul><li ng-repeat=\"message in logLine.messages['WARNING']\">{{message}}</li></ul></td><td style=\"width:25%\" sortable=\"'messagesErrorNb'\" data-title=\"'Error'\"><ul><li ng-repeat=\"message in logLine.messages['ERROR']\">{{message}}</li></ul></td></tr></table></div></div></div></tab><tab class=\"tab-color-lightgreen\"><tab-heading style=\"margin-left:25px\"><span>Questions (Erros :  {{total_question_error}} )</span></tab-heading><div style=\"border-top : 1px solid black\"><div class=\"element_table\"><table class=\"table\" style=\"width: 200px;\"><tr><td>Total Question with data imported</td><td>{{total_question}}</td></tr><tr><td>question with info</td><td>{{total_question_info}}</td></tr><tr><td>question with warning</td><td>{{total_question_warning}}</td></tr><tr><td>question with error</td><td>{{total_question_error}}</td></tr></table><div class=\"width:100%\" loading-container=\"tableParams2.settings().$loading\"><table class=\"table admin-table-import-bad width:100%\" ng-table=\"tableParams2\"><tr ng-repeat=\"logLine in $data\"><td style=\"width:12.5%\" sortable=\"'lineNb'\" data-title=\"'Line nb'\">{{logLine.lineNb}}</td><td style=\"width:12.5%\" sortable=\"'name'\" data-title=\"'Code'\">{{logLine.name}}</td><td style=\"width:25%\" sortable=\"'messagesInfoNb'\" data-title=\"'Info'\"><ul><li ng-repeat=\"message in logLine.messages['INFO']\">{{message}}</li></ul></td><td style=\"width:25%\" sortable=\"'messagesWarningNb'\" data-title=\"'Warinig'\"><ul><li ng-repeat=\"message in logLine.messages['WARNING']\">{{message}}</li></ul></td><td style=\"width:25%\" sortable=\"'messagesErrorNb'\" data-title=\"'Error'\"><ul><li ng-repeat=\"message in logLine.messages['ERROR']\">{{message}}</li></ul></td></tr></table></div></div></div></tab></tabset></div>");$templateCache.put('$/angular/templates/mm-awac-modal-invite-user.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 id=\"myModalLabel\" ng-bind-html=\"'USER_INVITATION_FORM_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"inviteEmailInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-field-text.html', "<div class=\"field_row\" ng-hide=\"getInfo().hidden === true\"><div class=\"field_cell\" ng-click=\"logField()\"><div class=\"question_info\" ng-show=\"getInfo().description != null\"><div class=\"question_info_popup\" ng-bind-html=\"getInfo().description | translate\"></div></div><span ng-bind-html=\"getInfo().fieldTitle | translate\"></span></div><div class=\"field_cell\"><div class=\"wrapper_table\"><div class=\"wrapper_row\"><div class=\"wrapper_cell wide\"><div class=\"error_message_numbers_only\" ng-show=\"errorMessage.length&gt;0\"><div>{{errorMessage}}</div><img src=\"/assets/images/question_field_error_message_icon_arrow.png\"></div><input ng-disabled=\"getInfo().disabled\" ng-attr-numbers-only=\"{{getInfo().numbersOnly}}\" placeholder=\"{{getInfo().placeholder | translateText}}\" focus-me=\"getInfo().focus()\" name=\"{{getInfo().inputName}}\" ng-class=\"{input_number: getInfo().numbersOnly === 'integer' || getInfo().numbersOnly === 'double'}\" ng-model=\"getInfo().field\" type=\"{{fieldType}}\" ng-hide=\"fieldType === 'textarea'\"><textarea ng-disabled=\"getInfo().disabled\" ng-attr-numbers-only=\"{{getInfo().numbersOnly}}\" placeholder=\"{{getInfo().placeholder | translateText}}\" focus-me=\"getInfo().focus()\" name=\"{{getInfo().inputName}}\" ng-class=\"{input_number: getInfo().numbersOnly === 'integer' || getInfo().numbersOnly === 'double'}\" ng-model=\"getInfo().field\" type=\"{{fieldType}}\" ng-show=\"fieldType === 'textarea'\"></textarea></div><div class=\"wrapper_cell\" ng-transclude></div></div></div></div><div class=\"field_cell\"><div ng-if=\"isValidationDefined\"><img src=\"/assets/images/field_valid.png\" ng-if=\"!hideIsValidIcon\" ng-show=\"getInfo().isValid\"><div class=\"error_message\" ng-hide=\"getInfo().isValid\"><img src=\"/assets/images/field_invalid.png\"><div ng-bind-html=\"getInfo().validationMessage | translate\"></div></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-help.html', "<!--Modal--><div class=\"modal-fullscreen\"><div class=\"modal-fullscreen-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button></div><div class=\"modal-fullscreen-body\"><div ng-include=\"url\"></div></div><div class=\"modal-fullscreen-footer\"></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-password-change.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 id=\"myModalLabel\" ng-bind-html=\"'PASSWORD_CHANGE_FORM_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"oldPasswordInfo\"></mm-awac-modal-field-text><br><mm-awac-modal-field-text ng-info=\"newPasswordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"newPasswordConfirmInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-question-comment.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 id=\"myModalLabel\" ng-bind-html=\"'MODAL_QUESTION_COMMENT_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><textarea ng-disabled=\"getParams().canBeEdited === false\" focus-me=\"true\" name=\"comment\" ng-model=\"comment\" class=\"question-comment-textarea\"></textarea></div></div><div class=\"modal-footer\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-hide=\"getParams().canBeEdited === false\"></button></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-password-confirmation.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 id=\"myModalLabel\" ng-bind-html=\"getParams().title | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"desc\" ng-bind-html=\"getParams().desc | translate\" ng-show=\"getParams().desc != null\"></div><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.myPassword\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CLOSE_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-edit-site.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 id=\"myModalLabel\" ng-bind-html=\"'EDIT_SITE_TITLE_CREATE' | translate\" class=\"modal-title\" ng-show=\"createNewSite === true\"></h4><h4 id=\"myModalLabel\" ng-bind-html=\"'EDIT_SITE_TITLE_EDIT' | translate\" class=\"modal-title\" ng-hide=\"createNewSite === true\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.name\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.description\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.nace\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.percentOwned\"></mm-awac-modal-field-text><div class=\"field_row\"><div class=\"field_cell\" ng-bind-html=\"'SITE_MANAGER_ORGANIZATIONAL_STRUCTURE' | translate\"></div><div class=\"field_cell\"><select ng-options=\"p  as ( p | translateText ) for p  in organizationStructureList \" ng-model=\"organizationStructure\"></select></div></div></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-create-reduction-action.html', "<div class=\"modal create_or_edit_action\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 id=\"myModalLabel\" ng-bind-html=\"(editMode ? 'EDIT_REDUCTION_ACTION_FORM_TITLE' : 'CREATE_REDUCTION_ACTION_FORM_TITLE') | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><!--Title (text)--><mm-awac-modal-field-text ng-info=\"title\"></mm-awac-modal-field-text><!--Type (radio): 'GES Reduction' or 'Better Method'--><div class=\"field_row field_form_separator\"><div class=\"field_cell\" ng-click=\"logField()\"><span ng-bind-html=\"typeKey.fieldTitle | translate\"></span></div><div class=\"field_cell field_wide\"><div><input name=\"{{typeKey.inputName}}\" value=\"1\" ng-model=\"typeKey.field\" type=\"radio\"><span>&nbsp;</span><span ng-bind-html=\"'REDUCTION_ACTION_TYPE_REDUCING_GES' | translate\"></span><div class=\"field_info\"><div class=\"field_info_popup\" ng-bind-html=\"'REDUCTION_ACTION_TYPE_REDUCING_GES_DESC' | translate\"></div></div></div><div><input name=\"{{typeKey.inputName}}\" value=\"2\" ng-model=\"typeKey.field\" type=\"radio\"><span>&nbsp;</span><span ng-class=\"{selected: typeKey.field == '2'}\" ng-bind-html=\"'REDUCTION_ACTION_TYPE_BETTER_MEASURE' | translate\"></span><div class=\"field_info\"><div class=\"field_info_popup\" ng-bind-html=\"'REDUCTION_ACTION_TYPE_BETTER_MEASURE_DESC' | translate\"></div></div></div></div></div><!--Status (radio): 'running', 'done' or 'canceled' (only in edit mode)--><div class=\"field_row field_form_separator\" ng-show=\"editMode\"><div class=\"field_cell\" ng-click=\"logField()\"><span ng-bind-html=\"statusKey.fieldTitle | translate\"></span></div><div class=\"field_cell field_wide\"><div ng-repeat=\"statusOption in statusOptions\"><input name=\"{{statusKey.inputName}}\" value=\"{{statusOption.key}}\" ng-model=\"statusKey.field\" type=\"radio\"><span>&nbsp;</span><span ng-bind-html=\"statusOption.label\"></span></div></div></div><!--Scope Type (radio): 'organization' or 'site'--><div class=\"field_row field_form_separator\"><div class=\"field_cell\" ng-click=\"logField()\"><span ng-bind-html=\"scopeTypeKey.fieldTitle | translate\"></span></div><div class=\"field_cell field_wide\"><div><input name=\"{{scopeTypeKey.inputName}}\" value=\"1\" ng-model=\"scopeTypeKey.field\" type=\"radio\"><span>&nbsp;</span><span ng-bind-html=\"'REDUCTION_ACTION_SCOPE_TYPE_ORG' | translate\"></span><span>&nbsp;</span><span ng-bind-html=\" ': &lt;b&gt;' + $root.organizationName + '&lt;/b&gt;' \"></span></div><div ng-show=\"$root.instanceName == 'enterprise'\"><input name=\"{{scopeTypeKey.inputName}}\" value=\"2\" ng-model=\"scopeTypeKey.field\" type=\"radio\"><span>&nbsp;</span><span ng-bind-html=\"'REDUCTION_ACTION_SCOPE_TYPE_SITE' | translate\"></span><span>&nbsp;</span><select ng-disabled=\"scopeTypeKey.field == '1'\" ng-options=\"s.id as s.name for s in $root.mySites\" ng-model=\"scopeId.field\"></select></div></div></div><!--Physical measure (text) (action description: ghg reduction objective, better reporting method description...)--><mm-awac-modal-field-text ng-info=\"physicalMeasure\" class=\"field_form_separator\"></mm-awac-modal-field-text><!--GHG Benefit (double with unit) (disabled if Type is not 'GES Reduction')--><mm-awac-modal-field-text ng-disabled=\"typeKey.field == '2'\" ng-info=\"ghgBenefit\" class=\"field_form_separator\"><select ng-disabled=\"$parent.typeKey.field == '2'\" ng-options=\"u.code as u.name for u in $parent.gwpUnits\" ng-model=\"$parent.ghgBenefitUnitKey\"></select></mm-awac-modal-field-text><!--Cost & Benefits--><mm-awac-modal-field-text ng-info=\"financialBenefit\" class=\"field_form_separator\"><div style=\"line-height: 22px;\">euros</div></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"investmentCost\"><div style=\"line-height: 22px;\">euros</div></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"expectedPaybackTime\"></mm-awac-modal-field-text><!--Due date--><mm-field-date ng-info=\"dueDate\" class=\"field_form_separator\"></mm-field-date><!--Other infos--><mm-awac-modal-field-text ng-info=\"webSite\" class=\"field_form_separator\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"responsiblePerson\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"comment\"></mm-awac-modal-field-text><div class=\"field_row\"><div class=\"field_cell\" ng-click=\"logField()\"><span ng-bind-html=\"'REDUCTION_ACTION_ATTACHMENTS_FIELD_TITLE' | translate\"></span></div><div class=\"field_cell field_wide\"><div class=\"document-question-progress-percentage\" ng-show=\"inDownload=== true &amp;&amp; percent != 100\">{{percent}} %</div><div ng-bind-html=\"'REDUCTION_ACTION_UPLOAD_IN_TREATMENT' | translate\" ng-show=\"inDownload=== true &amp;&amp; percent == 100\"></div><input ng-file-select=\"onFileSelect($files)\" type=\"file\"><table style=\"margin-top: 10px;\"><tr ng-repeat=\"f in files\"><td style=\"padding: 5px;\"><button class=\"button\" ng-click=\"download(f.id)\" type=\"button\"><span class=\"fa fa-download\"></span></button><button class=\"button\" ng-click=\"remove(f.id)\" type=\"button\"><span class=\"fa fa-remove\"></span></button><span>&nbsp;&nbsp;</span><b>{{ f.name }}</b></td></tr></table></div></div></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-verification-assign.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'VERIFICATION_REQUEST_ASSIGN' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><table class=\"associate-user-table\"><thead><tr><td><div ng-bind-html=\"'SITE_MANAGER_ADD_SELECTED_LABEL' | translate\"></div></td><td><div ng-bind-html=\"'NAME' | translate\"></div></td></tr></thead><tbody><tr ng-repeat=\"user in users\"><td><input name=\"a\" ng-model=\"user.selected\" ng-value=\"user.identifier\" type=\"checkbox\"></td><td>{{user.firstName}} {{user.lastName}}</td></tr></tbody></table></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-edit-event.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'EDIT_EVENT_TITLE_CREATE' | translate\" class=\"modal-title\" ng-show=\"createNewEvent === true\"></h4><h4 ng-bind-html=\"'EDIT_EVENT_TITLE_EDIT' | translate\" class=\"modal-title\" ng-hide=\"createNewEvent === true\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.name\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.description\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-request-verification.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 id=\"myModalLabel\" ng-bind-html=\"'ASK_VERIFICATION' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.email\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.comment\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.myPhoneNumber\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.myPassword\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CLOSE_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-email-change.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 id=\"myModalLabel\" ng-bind-html=\"'EMAIL_CHANGE_FORM_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text><br><mm-awac-modal-field-text ng-info=\"oldEmailInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"newEmailInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-verification-reject.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 id=\"myModalLabel\" ng-bind-html=\"'MODAL_QUESTION_COMMENT_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><textarea ng-disabled=\"getParams().readOnly === true\" focus-me=\"true\" name=\"comment\" ng-model=\"comment\" class=\"question-comment-textarea\"></textarea></div></div><div class=\"modal-footer\"><div ng-hide=\"getParams().readOnly === true\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-hide=\"getParams().canBeEdited === false\"></button></div><div ng-show=\"getParams().readOnly === true\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CLOSE_BUTTON' | translate\" type=\"button\"></button></div></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-consult-event.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'ORGANIZATION_EVENT' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><table class=\"admin-table-import-bad table\" ng-table=\"tableParams\" ng-show=\"events.length &gt; 0 \"><tr ng-repeat=\"event in $data\"><td sortable=\"'name'\" data-title=\"'EVENT' | translateText\">{{event.name}}</td><td sortable=\"'period.label'\" data-title=\"'SITE_MANAGER_EVENT_PERIOD'| translateText\">{{event.period.label}}</td><td data-title=\"'SITE_MANAGER_EVENT_DESCRIPTION'| translateText\">{{event.description}}</td></tr></table></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CLOSE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-add-user-site.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'SITE_MANAGER_ADD_USER_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><table class=\"associate-user-table\"><thead><tr><td ng-bind-html=\"'SITE_MANAGER_ADD_NAME_LABEL' | translate\"></td><td ng-bind-html=\"'SITE_MANAGER_ADD_LOGIN_LABEL' | translate\"></td><td ng-bind-html=\"'SITE_MANAGER_ADD_SELECTED_LABEL' | translate\"></td></tr></thead><tbody><tr ng-repeat=\"account in accounts\"><td> {{account.person.firstName}} {{account.person.lastName}}</td><td> {{account.identifier}}</td><td><input ng-click=\"toggleSelection(account)\" name=\"{{account.identifier}}\" value=\"{{account.identifier}}\" type=\"checkbox\" ng-checked=\"selection.indexOf(account) &gt; -1\"></td></tr></tbody></table><table class=\"associate-user-table\"><thead><tr><td ng-bind-html=\"'SITE_MANAGER_ADD_LIST_LABEL' | translate\"></td></tr></thead><tbody><tr ng-repeat=\"account in selection\"><td>{{account.identifier}}</td></tr></tbody></table></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-verification-confirmation.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save(true)\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'VERIFICATION_CUSTOMER_CONFIRMATION_WINDOW_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div ng-bind-html=\"'VERIFICATION_CUSTOMER_CONFIRMATION_WINDOW_DESC' | translateWithVars: [$root.verificationRequest.organizationVerifier.name]\"></div><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.myPassword\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save(true)\" ng-bind-html=\"'VERIFICATION_CUSTOMER_CONFIRMATION_WINDOW_ACCEPT' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save(false)\" ng-bind-html=\"'VERIFICATION_CUSTOMER_CONFIRMATION_WINDOW_REJECT' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-verification-finalization.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'FINALIZE_VERIFICATION_BUTTON' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"desc\" ng-bind-html=\"'FINALIZATION_VALID_DESC' | translate\" ng-show=\"getParams().verificationSuccess == true\"></div><div class=\"desc\" ng-bind-html=\"'FINALIZATION_UNVALID_DESC' | translate\" ng-show=\"getParams().verificationSuccess != true\"></div><div><div class=\"field_form\"><div class=\"field_row\" ng-show=\"getParams().verificationSuccess == true\"><div ng-bind-html=\"'VERIFICATION_REPORT' | translate\"></div><div><div class=\"document-question-progress-percentage\" ng-show=\"inDownload=== true &amp;&amp; percent != 100\">{{percent}} %</div><div ng-bind-html=\"'QUESTION_FILE_TREATEMENT' | translate\" ng-show=\"inDownload=== true &amp;&amp; percent == 100\"></div><input ng-file-select=\"onFileSelect($files)\" type=\"file\"></div></div><mm-awac-modal-field-text ng-info=\"comment\"></mm-awac-modal-field-text></div></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-manager.html', "<div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-verification-document.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save(true)\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 id=\"myModalLabel\" ng-bind-html=\"'VALID_REQUEST' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"desc\" ng-bind-html=\"'VALID_REQUEST_DESC' | translate\"></div><div class=\"field_form\"><div class=\"field_row\"><div></div><div><button class=\"button\" ng-click=\"download()\" ng-bind-html=\"'DOWNLOAD_VERIFICATION_REPORT' | translate\"></button></div></div><mm-awac-modal-field-text ng-info=\"fields.myPassword\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CLOSE_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save(true)\" ng-bind-html=\"'VERIFICATION_CUSTOMER_CONFIRMATION_WINDOW_ACCEPT' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save(false)\" ng-bind-html=\"'VERIFICATION_CUSTOMER_CONFIRMATION_WINDOW_REJECT' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-connection-password-change.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 id=\"myModalLabel\" ng-bind-html=\"'PASSWORD_CHANGE_FORM_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div ng-bind-html=\"'CONNECTION_PASSWORD_CHANGE_FORM_DESC' | translate\"></div><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"newPasswordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"newPasswordConfirmInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-confirm-closing.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"valid()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'UNCLOSING_FORM' | translate\" class=\"modal-title\" ng-show=\"$root.closedForms\"></h4><h4 ng-bind-html=\"'CLOSING_FORM' | translate\" class=\"modal-title\" ng-hide=\"$root.closedForms\"></h4></div><div class=\"modal-body\"><p ng-bind-html=\"'MODAL_CONFIRM_UNCLOSING_DESC'| translate\" ng-show=\"$root.closedForms\"></p><p ng-bind-html=\"'MODAL_CONFIRM_CLOSING_DESC'| translate\" ng-hide=\"$root.closedForms\"></p><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"valid();\" ng-bind-html=\"'SUBMIT' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-document-manager.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'MODAL_DOCUMENT_MANAGER_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><table class=\"document-manager-table\"><thead><tr><td ng-bind-html=\"'MODAL_DOCUMENT_MANAGER_DOC_NAME' | translate\"></td><td ng-bind-html=\"'MODAL_DOCUMENT_MANAGER_ACTION' | translate\"></td></tr></thead><tbody><tr ng-repeat=\"(key,value) in listDocuments\"><td>{{value}}</td><td><button class=\"button\" ng-click=\"download(key)\" ng-bind-html=\"'MODAL_DOCUMENT_MANAGER_DOWNLOAD' | translate\" type=\"button\">)</button><button class=\"button\" ng-click=\"removeDoc(key)\" ng-bind-html=\"'MODAL_DOCUMENT_MANAGER_REMOVE' | translate\" type=\"button\" ng-hide=\"getParams().readyOnly === true\">)</button></td></tr></tbody></table></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-confirm-dialog.html', "<div class=\"modal confirm-dialog\" ng-escape=\"close()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><h5 ng-bind-html=\"titleKey | translate\" class=\"modal-title\"></h5></div><div class=\"modal-body\"><div class=\"desc\" ng-bind-html=\"messageKey | translate\"></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"confirm();\" ng-bind-html=\"'YES_BUTTON' | translate\" type=\"button\"></button><span>&nbsp;</span><button class=\"button btn btn-primary\" autofocus=\"autofocus\" ng-click=\"close();\" ng-bind-html=\"'NO_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-verification-finalization-visualization.html', "<!--Modal--><!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'FINALIZE_VERIFICATION_BUTTON' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><textarea ng-disabled=\"true\" focus-me=\"true\" name=\"comment\" ng-model=\"comment\" class=\"question-comment-textarea\"></textarea></div></div><div class=\"modal-footer\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-loading.html', "<!--Modal--><div class=\"modal\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\" style=\"text-align:center\"><h4 ng-bind-html=\"'LOADING' | translate\"></h4></div><div class=\"modal-body\" style=\"text-align:center\"><img src=\"/assets/images/loading_preorganization.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-confirmation-exit-form.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;<span</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'MODAL_CONFIRMATION_EXIT_FORM_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\" ng-bind-html=\"'MODAL_CONFIRMATION_EXIT_FORM_MESSAGE' | translate\"></div></div><div class=\"modal-footer\"><button class=\"button\" ng-click=\"continue();\" ng-bind-html=\"'MODAL_CONFIRMATION_EXIT_FORM_CONTINUE' | translate\" type=\"button\"></button><button class=\"button\" ng-click=\"save()\" focus-me=\"true\" ng-bind-html=\"'MODAL_CONFIRMATION_EXIT_FORM_SAVE' | translate\" type=\"button\"></button><button class=\"button\" ng-click=\"close()\" ng-bind-html=\"'MODAL_CONFIRMATION_EXIT_FORM_CANCEL' | translate\" type=\"button\"></button></div></div></div></div>");$templateCache.put('$/angular/templates/mm-field-date.html', "<div class=\"field_row\" ng-hide=\"getInfo().hidden === true\"><div class=\"field_cell\" ng-click=\"logField()\" ng-bind-html=\"getInfo().fieldTitle | translate\"></div><div class=\"field_cell\"><div class=\"dropdown\"></div><a class=\"dropdown-toggle\" data-target=\"#\" id=\"{{id}}\" role=\"button\" data-toggle=\"dropdown\" href=\"\"><div class=\"input-group\"><input class=\"form-control\" ng-model=\"resultFormated\" type=\"text\"><span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-calendar\"></i></span></div><ul class=\"dropdown-menu date_input\" aria-labelledby=\"dLabel\" role=\"menu\"><datetimepicker data-ng-model=\"result\" data-datetimepicker-config=\"{ dropdownSelector: '{{idHtag}}',minView:'day' }\"></datetimepicker></ul></a><div ng-transclude></div></div><div class=\"field_cell\"><img src=\"/assets/images/field_valid.png\" ng-if=\"!getInfo().hideIsValidIcon\" ng-show=\"getInfo().isValid\"><div class=\"error_message\" ng-hide=\"getInfo().isValid\"><img src=\"/assets/images/field_invalid.png\"><div>{{getInfo().validationMessage | translateText}}</div></div></div></div>");$templateCache.put('$/angular/templates/mm-button.html', "<button class=\"button\" ng-click=\"ngClick\" ng-class=\"ngClass\" type=\"button\"><span class=\"fa fa-{{ getIcon() }}\"></span><span>&nbsp;</span><span ng-bind-html=\"getCode() | translate\"></span></button>");$templateCache.put('$/angular/templates/mm-awac-tab-progress-bar.html', "<div class=\"tab-pg-bar\"><div class=\"tab-pg-text\"><span ng-bind-html=\"'FILLED_BY' | translate\"></span><span>&nbsp;</span><span>{{ pg }}%</span></div><div class=\"tab-pg-background tab-pb-{{color}}-bg\"><div style=\"width: {{ pg }}%\" class=\"tab-pg-indicator tab-pb-{{color}}-fg\"></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-graph-donut.html', "<table><tr><td><canvas class=\"holder\" height=\"200\" width=\"400\"></canvas></td><td class=\"chart-legend\"><b ng-bind-html=\"'GRAPH_LEGEND' | translate\"></b><div ng-bind-html=\"legend\"></div></td></tr></table>");$templateCache.put('$/angular/templates/mm-awac-registration-municipality.html', "<div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"emailInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"identifierInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordConfirmInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"municipalityNameInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><br><div class=\"field_row field_form_separator\"><div class=\"field_cell\" ng-bind-html=\"'ORGANIZATION_STATISTICS_ALLOWED' | translateText\"></div><div class=\"field_cell\" style=\"text-align: left;\"><input style=\"width: 18px;height: 18px;margin-top: 2px;\" ng-model=\"organizationStatisticsAllowed\" type=\"checkbox\"></div></div><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-disabled=\"!registrationFieldValid()\" ng-click=\"registration()\" ng-bind-html=\"'REGISTRATION_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div>");$templateCache.put('$/angular/templates/mm-awac-registration-enterprise.html', "<div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"emailInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"identifierInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordConfirmInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"organizationNameInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"firstSiteNameInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><br><div class=\"field_row field_form_separator\"><div class=\"field_cell\" ng-bind-html=\"'ORGANIZATION_STATISTICS_ALLOWED' | translateText\"></div><div class=\"field_cell\" style=\"text-align: left;\"><input style=\"width: 18px;height: 18px;margin-top: 2px;\" ng-model=\"organizationStatisticsAllowed\" type=\"checkbox\"></div></div><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-disabled=\"!registrationFieldValid()\" ng-click=\"registration()\" ng-bind-html=\"'REGISTRATION_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div>");$templateCache.put('$/angular/templates/mm-awac-result-table.html', "<ng-virtual><table class=\"indicators_table\"><thead><tr><th></th><th colspan=\"4\"><span class=\"period-header\" style=\"color: {{ ngModel.leftColor }}; border-bottom-color: {{ ngModel.leftColor }}\">{{ngModel.leftPeriod}}</span></th><th colspan=\"4\" ng-show=\"ngModel.rightPeriod!=null\"><span class=\"period-header\" style=\"color: {{ ngModel.rightColor }}; border-bottom-color: {{ ngModel.rightColor }}\">{{ngModel.rightPeriod}}</span></th></tr><tr><th></th><th class=\"align-right scope1\" style=\"color: {{ ngModel.leftColor }}\"><div><span class=\"wrapped\"><span ng-bind-html=\"'SCOPE_1' | translate\"></span><span> (tCO2e)</span></span></div></th><th class=\"align-right scope2\" style=\"color: {{ ngModel.leftColor }}\"><div><span class=\"wrapped\"><span ng-bind-html=\"'SCOPE_2' | translate\"></span><span> (tCO2e)</span></span></div></th><th class=\"align-right scope3\" style=\"color: {{ ngModel.leftColor }}\"><div><span class=\"wrapped\"><span ng-bind-html=\"'SCOPE_3' | translate\"></span><span> (tCO2e)</span></span></div></th><th class=\"align-right out-of-scope\" style=\"color: {{ ngModel.leftColor }}\"><div><span class=\"wrapped\"><span ng-bind-html=\"'OUT_OF_SCOPE' | translate\"></span><span> (tCO2)</span></span></div></th><th class=\"align-right scope1\" style=\"color: {{ ngModel.rightColor }}\" ng-show=\"ngModel.rightPeriod!=null\"><div><span class=\"wrapped\"><span ng-bind-html=\"'SCOPE_1' | translate\"></span><span> (tCO2e)</span></span></div></th><th class=\"align-right scope2\" style=\"color: {{ ngModel.rightColor }}\" ng-show=\"ngModel.rightPeriod!=null\"><div><span class=\"wrapped\"><span ng-bind-html=\"'SCOPE_2' | translate\"></span><span> (tCO2e)</span></span></div></th><th class=\"align-right scope3\" style=\"color: {{ ngModel.rightColor }}\" ng-show=\"ngModel.rightPeriod!=null\"><div><span class=\"wrapped\"><span ng-bind-html=\"'SCOPE_3' | translate\"></span><span> (tCO2e)</span></span></div></th><th class=\"align-right out-of-scope\" style=\"color: {{ ngModel.rightColor }}\" ng-show=\"ngModel.rightPeriod!=null\"><div><span class=\"wrapped\"><span ng-bind-html=\"'OUT_OF_SCOPE' | translate\"></span><span> (tCO2)</span></span></div></th></tr></thead><tbody><tr ng-show=\"showAll || (rl.leftScope1Value + rl.leftScope2Value + rl.leftScope3Value + rl.leftOutOfScopeValue + rl.rightScope1Value + rl.rightScope2Value + rl.rightScope3Value + rl.rightOutOfScopeValue &gt; 0)\" ng-repeat=\"rl in ngModel.reportLines | orderBy:'order'\"><td><span ng-bind-html=\"rl.indicatorName | translate\"></span></td><td class=\"align-right scope1\" style=\"color: {{ ngModel.leftColor }}\"><span ng-bind-html=\"rl.leftScope1Value | numberToI18NOrLess\" ng-show=\"rl.leftScope1Value &gt; 0\"></span></td><td class=\"align-right scope2\" style=\"color: {{ ngModel.leftColor }}\"><span ng-bind-html=\"rl.leftScope2Value | numberToI18NOrLess\" ng-show=\"rl.leftScope2Value &gt; 0\"></span></td><td class=\"align-right scope3\" style=\"color: {{ ngModel.leftColor }}\"><span ng-bind-html=\"rl.leftScope3Value | numberToI18NOrLess\" ng-show=\"rl.leftScope3Value &gt; 0\"></span></td><td class=\"align-right out-of-scope\" style=\"color: {{ ngModel.leftColor }}\"><span ng-bind-html=\"rl.leftOutOfScopeValue | numberToI18NOrLess\" ng-show=\"rl.leftOutOfScopeValue &gt; 0\"></span></td><td class=\"align-right scope1\" style=\"color: {{ ngModel.rightColor }}\" ng-show=\"ngModel.rightPeriod!=null\"><span ng-bind-html=\"rl.rightScope1Value | numberToI18NOrLess\" ng-show=\"rl.rightScope1Value &gt; 0\"></span></td><td class=\"align-right scope2\" style=\"color: {{ ngModel.rightColor }}\" ng-show=\"ngModel.rightPeriod!=null\"><span ng-bind-html=\"rl.rightScope2Value | numberToI18NOrLess\" ng-show=\"rl.rightScope2Value &gt; 0\"></span></td><td class=\"align-right scope3\" style=\"color: {{ ngModel.rightColor }}\" ng-show=\"ngModel.rightPeriod!=null\"><span ng-bind-html=\"rl.rightScope3Value | numberToI18NOrLess\" ng-show=\"rl.rightScope3Value &gt; 0\"></span></td><td class=\"align-right out-of-scope\" style=\"color: {{ ngModel.rightColor }}\" ng-show=\"ngModel.rightPeriod!=null\"><span ng-bind-html=\"rl.rightOutOfScopeValue | numberToI18NOrLess\" ng-show=\"rl.rightOutOfScopeValue &gt; 0\"></span></td></tr></tbody><tfoot><tr><td style=\"text-align: right\"><span ng-bind-html=\"'RESULTS_TOTAL' | translate\"></span></td><td class=\"align-right scope1\" style=\"color: {{ ngModel.leftColor }}\"><span ng-bind-html=\"getLeftTotalScope1() | numberToI18NOrLess\"></span></td><td class=\"align-right scope2\" style=\"color: {{ ngModel.leftColor }}\"><span ng-bind-html=\"getLeftTotalScope2() | numberToI18NOrLess\"></span></td><td class=\"align-right scope3\" style=\"color: {{ ngModel.leftColor }}\"><span ng-bind-html=\"getLeftTotalScope3() | numberToI18NOrLess\"></span></td><td class=\"align-right out-of-scope\" style=\"color: {{ ngModel.leftColor }}\"><span ng-bind-html=\"getLeftTotalOutOfScope() | numberToI18NOrLess\"></span></td><td class=\"align-right scope1\" style=\"color: {{ ngModel.rightColor }}\" ng-show=\"ngModel.rightPeriod!=null\"><span ng-bind-html=\"getRightTotalScope1() | numberToI18NOrLess\"></span></td><td class=\"align-right scope2\" style=\"color: {{ ngModel.rightColor }}\" ng-show=\"ngModel.rightPeriod!=null\"><span ng-bind-html=\"getRightTotalScope2() | numberToI18NOrLess\"></span></td><td class=\"align-right scope3\" style=\"color: {{ ngModel.rightColor }}\" ng-show=\"ngModel.rightPeriod!=null\"><span ng-bind-html=\"getRightTotalScope3() | numberToI18NOrLess\"></span></td><td class=\"align-right out-of-scope\" style=\"color: {{ ngModel.rightColor }}\" ng-show=\"ngModel.rightPeriod!=null\"><span ng-bind-html=\"getRightTotalOutOfScope() | numberToI18NOrLess\"></span></td></tr><tr><td></td><td class=\"align-right scope1\" colspan=\"4\" style=\"color: {{ ngModel.leftColor }}\"><span class=\"period-footer\" style=\"color: {{ ngModel.leftColor }}; border-top-color: {{ ngModel.leftColor }}\"></span></td><td class=\"align-right scope1\" colspan=\"4\" style=\"color: {{ ngModel.rightColor }}\" ng-show=\"ngModel.rightPeriod!=null\"><span class=\"period-footer\" style=\"color: {{ ngModel.rightColor }}; border-top-color: {{ ngModel.rightColor }}\"></span></td></tr></tfoot></table><div><input ng-model=\"showAll\" type=\"checkbox\"><label><span ng-bind-html=\"'SHOW_ALL_INDICATORS' | translate\"></span></label></div></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-calculator-survey.html', "<div><div ng-hide=\"$root.hideHeader()\"><div class=\"survey-header\"><!--user block--><table class=\"survey-header-option\"><tr><td rowspan=\"2\"><div class=\"wallonie_logo\"></div></td><td rowspan=\"2\"><div class=\"awac_logo\"></div></td><td class=\"wide align-left\"><div class=\"calculateur_type\" ng-bind-html=\"'TITLE_ENTERPRISE' | translate\" ng-show=\"$root.instanceName == 'enterprise'\"></div><div class=\"calculateur_type\" ng-bind-html=\"'TITLE_MUNICIPALITY' | translate\" ng-show=\"$root.instanceName == 'municipality'\"></div></td><td><select class=\"language-chooser\" ng-options=\"l.value as l.label for l in $root.languages\" ng-model=\"$root.language\"></select></td><td><div class=\"survey-interface-management\" ng-bind-html=\"'SURVEY_INTERFACE_MANAGEMENT' | translate\"></div></td><td><div class=\"login-info\"><div class=\"connected\" ng-show=\"$root.currentPerson!=null\"><span ng-bind-html=\"'WELCOME' | translate\"></span>,<span class=\"username\">{{$root.currentPerson.firstName}} {{$root.currentPerson.lastName}}</span></div><div class=\"not-connected\" ng-show=\"$root.currentPerson==null\">Your are currently not connected</div></div></td></tr><tr><td><div class=\"entreprise_name\">{{ $root.organizationName }}</div></td><td><button class=\"button confidentiality\" ng-click=\"$root.showConfidentiality()\" ng-bind-html=\"'SURVEY_INTERFACE_CONFIDENTIALITY' | translate\" type=\"button\"></button><button class=\"button help\" ng-click=\"$root.showHelp()\" ng-bind-html=\"'SURVEY_INTERFACE_ASSISTANCE' | translate\" type=\"button\"></button></td><td><!--organization manager button--><button class=\"button user_manage\" ng-disabled=\"$root.currentPerson.isAdmin === false\" ng-click=\"isDisabled || $root.nav('/organization_manager')\" ng-class=\"{'selected': isMenuCurrentlySelected('/organization_manager') == true}\" ng-bind-html=\"'ORGANIZATION_MANAGER_BUTTON' | translate\" type=\"button\"></button><!--site manager button--><button class=\"button user_manage\" ng-disabled=\"$root.currentPerson.isAdmin === false\" ng-click=\"isDisabled || $root.nav('/site_manager')\" ng-class=\"{'selected': isMenuCurrentlySelected('/site_manager') == true}\" ng-bind-html=\"'SITE_MANAGER_BUTTON' | translate\" type=\"button\" ng-show=\"$root.instanceName == 'enterprise'\"></button><!--user manager button--><button class=\"button user_manage\" ng-disabled=\"$root.currentPerson.isAdmin === false\" ng-click=\"isDisabled || $root.nav('/user_manager')\" ng-class=\"{'selected': isMenuCurrentlySelected('/user_manager') == true}\" ng-bind-html=\"'USER_MANAGER_BUTTON' | translate\" type=\"button\"></button></td><td><!--user data button--><button class=\"button user_manage\" ng-click=\"$root.nav('/user_data')\" ng-class=\"{'selected': isMenuCurrentlySelected('/user_data') == true}\" ng-bind-html=\"'USER_DATA_BUTTON' | translate\" type=\"button\"></button><!--logout button--><button class=\"button user_manage\" ng-click=\"$root.logout();\" ng-bind-html=\"'LOGOUT_BUTTON' | translate\" type=\"button\" ng-show=\"$root.currentPerson!=null\"></button></td></tr></table></div><div class=\"data_menu\" ng-show=\"displayLittleMenu===true || displayMenu===true\"><div class=\"data_date\"><div ng-bind-html=\"'PERIOD_DATA' | translate\"></div><select ng-options=\"p.key as p.label for p in $root.availablePeriods\" ng-model=\"$root.periodSelectedKey\"></select></div><div class=\"big_separator\" ng-show=\"$root.instanceName == 'enterprise'\"></div><div class=\"data_date_compare\" ng-show=\"$root.instanceName == 'enterprise'\"><div ng-bind-html=\"'SURVEY_INTERFACE_SELECTED_SITE' | translate\"></div><select ng-options=\"s.scope as s.name for s in $root.mySites\" ng-model=\"$root.scopeSelectedId\"></select></div><div class=\"big_separator\"></div><div class=\"data_date_compare\"><div ng-bind-html=\"'SURVEY_INTERFACE_COMPARE_TO' | translate\"></div><select ng-options=\"p.key as p.label for p in periodsForComparison\" ng-model=\"$root.periodToCompare\"></select></div><div class=\"big_separator\"></div><div ng-show=\"$root.currentPerson.isAdmin == true\"><!--verification button => start verification--><button class=\"button verification\" ng-disabled=\"$root.closeableForms !== true\" ng-click=\"isDisabled || requestVerification()\" ng-bind-html=\"'ASK_VERIFICATION' | translate\" type=\"button\" ng-show=\"$root.getVerificationRequestStatus()==null\"></button><!--re-submit => is case of correction--><button class=\"button verification\" ng-disabled=\"$root.closeableForms !== true\" ng-click=\"isDisabled || resubmitVerification()\" ng-bind-html=\"'VERIFICATION_RE_SUBMIT_BUTTON' | translate\" type=\"button\" ng-show=\"$root.getVerificationRequestStatus()==='VERIFICATION_STATUS_CORRECTION'\"></button><!--consult verificaiont => is verified--><button class=\"button verification\" ng-click=\"isDisabled || consultVerification()\" ng-bind-html=\"'SURVEY_INTERFACE_VERIFICATION_CONSULT' | translate\" type=\"button\" ng-show=\"$root.getVerificationRequestStatus()==='VERIFICATION_STATUS_WAIT_CUSTOMER_VERIFIED_CONFIRMATION'\"></button><!--accept / reject validation--><button class=\"button verification\" ng-click=\"isDisabled || confirmVerifier()\" ng-bind-html=\"'SURVEY_INTERFACE_VERIFICATION_CONFIRM_VERIFIER' | translate\" type=\"button\" ng-show=\"$root.getVerificationRequestStatus()==='VERIFICATION_STATUS_WAIT_CUSTOMER_CONFIRMATION'\"></button><!--cancel verification => other case--><button class=\"button verification\" ng-click=\"isDisabled || cancelVerification()\" ng-bind-html=\"'VERIFICATION_CANCEL_BUTTON' | translate\" type=\"button\" ng-show=\"$root.getVerificationRequestStatus()!=null &amp;&amp; $root.getVerificationRequestStatus()!='VERIFICATION_STATUS_WAIT_CUSTOMER_VERIFIED_CONFIRMATION' &amp;&amp; $root.getVerificationRequestStatus()!='VERIFICATION_STATUS_VERIFIED' &amp;&amp; $root.getVerificationRequestStatus()!='VERIFICATION_STATUS_CORRECTION' &amp;&amp; $root.getVerificationRequestStatus()!='VERIFICATION_STATUS_WAIT_CUSTOMER_CONFIRMATION'\"></button><!--consult verification faild comment--><button class=\"button verification\" ng-click=\"isDisabled || consultVerificationFinalComment()\" ng-bind-html=\"'VERIFICATION_FINALIZATION_CONSULT_COMMENT' | translate\" type=\"button\" ng-show=\"$root.getVerificationRequestStatus()==='VERIFICATION_STATUS_CORRECTION' \"></button><button class=\"button verification\" ng-click=\"isDisabled || $root.closeForms()\" ng-bind-html=\"'UNCLOSING_FORM' | translate\" type=\"button\" ng-show=\"$root.closedForms\"></button><button class=\"button verification\" ng-click=\"isDisabled || $root.closeForms()\" ng-bind-html=\"'CLOSING_FORM' | translate\" type=\"button\" ng-hide=\"$root.closedForms || $root.getVerificationRequestStatus()!==null\"></button></div><div class=\"big_separator\"></div><div class=\"data_save\"><div class=\"last_save\" ng-hide=\"lastSaveTime===null\"><span ng-bind-html=\"'LAST_SAVE' | translate\"></span><br>{{lastSaveTime | date: 'medium' }}</div><div class=\"small_separator\"></div><div class=\"save_button\"><button class=\"button save\" ng-click=\"save()\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div></div></div><div class=\"inject-menu\"></div></div><div class=\"{{getClassContent()}}\" ng-view></div><div class=\"footer\"></div></div>");$templateCache.put('$/angular/templates/mm-awac-verification-survey.html', "<div><div ng-hide=\"$root.hideHeader()\"><div class=\"survey-header\"><!--user block--><table class=\"survey-header-option\"><tr><td rowspan=\"2\"><div class=\"wallonie_logo\"></div></td><td rowspan=\"2\"><div class=\"awac_logo\"></div></td><td class=\"wide align-left\"><div class=\"calculateur_type\" ng-bind-html=\"'TITLE_VERIFICATION' | translate\"></div></td><td><div><select ng-options=\"l.value as l.label for l in $root.languages\" ng-model=\"$root.language\"></select></div></td><td><div ng-bind-html=\"'SURVEY_INTERFACE_MANAGEMENT' | translate\"></div></td><td><div ng-show=\"$root.currentPerson!=null\"><span ng-bind-html=\"'WELCOME' | translate\"></span>,<span class=\"username\">{{$root.currentPerson.firstName}} {{$root.currentPerson.lastName}}</span></div><div ng-show=\"$root.currentPerson==null\">Your are currently not connected</div></td></tr><tr><td><div class=\"entreprise_name\">{{ $root.organizationName }}</div></td><td><button class=\"button help\" ng-click=\"$root.showHelp()\" ng-bind-html=\"'SURVEY_INTERFACE_ASSISTANCE' | translate\" type=\"button\"></button></td><td><!--organization manager button--><button class=\"button user_manage\" ng-disabled=\"$root.currentPerson.isAdmin === false\" ng-click=\"isDisabled || $root.nav('/organization_manager')\" ng-class=\"{'selected': isMenuCurrentlySelected('/organization_manager') == true}\" ng-bind-html=\"'ORGANIZATION_MANAGER_BUTTON' | translate\" type=\"button\"></button><!--user manager button--><button class=\"button user_manage\" ng-disabled=\"$root.currentPerson.isAdmin === false\" ng-click=\"isDisabled || $root.nav('/user_manager')\" ng-class=\"{'selected': isMenuCurrentlySelected('/user_manager') == true}\" ng-bind-html=\"'USER_MANAGER_BUTTON' | translate\" type=\"button\"></button></td><td><!--user data button--><button class=\"button user_manage\" ng-click=\"$root.nav('/user_data')\" ng-class=\"{'selected': isMenuCurrentlySelected('/user_data') == true}\" ng-bind-html=\"'USER_DATA_BUTTON' | translate\" type=\"button\"></button><!--logout button--><button class=\"button user_manage\" ng-click=\"$root.logout();\" ng-bind-html=\"'LOGOUT_BUTTON' | translate\" type=\"button\" ng-show=\"$root.currentPerson!=null\"></button></td></tr></table></div><div class=\"verification-menu-background\"><div class=\"verification-menu\"><button class=\"button\" ng-disabled=\"$root.currentPerson.isAdmin !== true\" ng-click=\"isDisabled || $root.nav('/manage')\" ng-class=\"{'selected': isMenuCurrentlySelected('/manage') == true}\" ng-bind-html=\"'VERIFICATION_SURVEY_MANAGE' | translate\" type=\"button\"></button><button class=\"button\" ng-click=\"isDisabled || $root.nav('/verification')\" ng-class=\"{'selected': isMenuCurrentlySelected('/verification') == true}\" ng-bind-html=\"'VERIFICATION_SURVEY_VERIF' | translate\" type=\"button\"></button><button class=\"button\" ng-disabled=\"$root.currentPerson.isAdmin !== true &amp;&amp; $root.currentPerson.isMainVerifier !== true\" ng-click=\"isDisabled || $root.nav('/submit')\" ng-class=\"{'selected': isMenuCurrentlySelected('/submit') == true}\" ng-bind-html=\"'VERIFICATION_SURVEY_SUBMIT' | translate\" type=\"button\"></button><button class=\"button\" ng-click=\"isDisabled || $root.nav('/archive')\" ng-class=\"{'selected': isMenuCurrentlySelected('/archive') == true}\" ng-bind-html=\"'VERIFICATION_SURVEY_ARCHIVE' | translate\" type=\"button\"></button></div></div></div><div class=\"{{getClassContent()}}\" ng-view></div><div class=\"footer\"></div></div>");$templateCache.put('$/angular/templates/mm-awac-admin-survey.html', "<div><div ng-hide=\"$root.hideHeader()\"><div class=\"survey-header\"><!--user block--><table class=\"survey-header-option\"><tr><td rowspan=\"2\"><div class=\"wallonie_logo\"></div></td><td rowspan=\"2\"><div class=\"awac_logo\"></div></td><td class=\"wide align-left\"><div class=\"calculateur_type\" ng-bind-html=\"'TITLE_ADMIN' | translate\"></div></td><td><div><select ng-options=\"l.value as l.label for l in $root.languages\" ng-model=\"$root.language\"></select></div></td><td><div ng-bind-html=\"'SURVEY_INTERFACE_MANAGEMENT' | translate\"></div></td><td><div ng-show=\"$root.currentPerson!=null\"><span ng-bind-html=\"'WELCOME' | translate\"></span>,<span class=\"username\">{{$root.currentPerson.firstName}} {{$root.currentPerson.lastName}}</span></div><div ng-show=\"$root.currentPerson==null\">Your are currently not connected</div></td></tr><tr><td></td><td><button class=\"button help\" ng-click=\"$root.showHelp()\" ng-bind-html=\"'SURVEY_INTERFACE_ASSISTANCE' | translate\" type=\"button\"></button></td><td><!--user manager button--><button class=\"button user_manage\" ng-disabled=\"$root.currentPerson.isAdmin === false\" ng-click=\"isDisabled || $root.nav('/user_manager')\" ng-class=\"{'selected': isMenuCurrentlySelected('/user_manager') == true}\" ng-bind-html=\"'USER_MANAGER_BUTTON' | translate\" type=\"button\"></button></td><td><!--user data button--><button class=\"button user_manage\" ng-click=\"$root.nav('/user_data')\" ng-class=\"{'selected': isMenuCurrentlySelected('/user_data') == true}\" ng-bind-html=\"'USER_DATA_BUTTON' | translate\" type=\"button\"></button><!--logout button--><button class=\"button user_manage\" ng-click=\"$root.logout();\" ng-bind-html=\"'LOGOUT_BUTTON' | translate\" type=\"button\" ng-show=\"$root.currentPerson!=null\"></button></td></tr></table></div><div class=\"verification-menu-background\"><div class=\"verification-menu\"><button class=\"button\" ng-click=\"isDisabled || $root.nav('/driver')\" ng-class=\"{'selected': isMenuCurrentlySelected('/driver') == true}\" ng-bind-html=\"'ADMIN_SURVEY_DRIVER' | translate\" type=\"button\"></button></div></div></div><div class=\"{{getClassContent()}}\" ng-view></div><div class=\"footer\"></div></div>");$templateCache.put('$/angular/templates/mm-awac-sub-title.html', "<div><div class=\"sub_title\"><div class=\"question_info\" ng-show=\"hasDescription()\"><div class=\"question_info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-bind-html=\"getQuestionCode() | translate\"></span><ng-virtual ng-transclude></ng-virtual></div></div>");$templateCache.put('$/angular/templates/mm-awac-question.html', "<div class=\"question_field\" ng-class=\"{'twoanswer':displayOldDatas()===true, 'oneanswer':displayOldDatas()===false,'condition-false':getCondition() === false}\"><div><div class=\"question_info\" ng-show=\"hasDescription()\"><div class=\"question_info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-class=\"getIcon()\" class=\"glyphicon\"></span><span ng-click=\"logQuestionCode()\" ng-class=\"{optional : getOptional() === true}\" ng-bind-html=\"getQuestionCode() | translate\"></span></div><div><div class=\"status\" ng-class=\"getStatusClass()\"></div><div class=\"error_message_numbers_only\" ng-show=\"errorMessage.length&gt;0\"><div>{{errorMessage}}</div><img src=\"/assets/images/question_field_error_message_icon_arrow.png\"></div><span class=\"inject-data\"></span><div class=\"edit_comment_icon\"><button class=\"button edit_comment_icon glyphicon glyphicon-pencil\" ng-click=\"editComment(!isDisabled())\" name=\"{{ getQuestionCode() }}_COMMENT\" ng-class=\"{edit_comment_icon_selected:getAnswer().comment !=null}\" ng-hide=\"getAggregation()!=null || isDisabled() &amp;&amp; getAnswer().comment == null\"></button></div><div class=\"user_icon {{getUserClass()}}\" ng-hide=\"getAggregation()!=null || getAnswer().value == null\">{{getUserName(false,true)}}<div><span>{{getUserName(false,false)}}</span><img src=\"/assets/images/user_icon_arrow.png\"></div></div></div><div class=\"question_comparison\" ng-show=\"displayOldDatas() === true &amp;&amp; getAnswer(true) != null\"><div><button class=\"button\" title=\"Copier la valeur\" ng-click=\"copyDataToCompare()\" ng-hide=\"isDisabled()\"><<</button></div><span class=\"inject-data-to-compare\"></span><div class=\"edit_comment_icon\"><button class=\"button edit_comment_icon glyphicon glyphicon-pencil edit_comment_icon_selected\" ng-click=\"editComment(false)\" name=\"OLD_{{ getQuestionCode() }}_COMMENT\" ng-hide=\"getAggregation()!=null || getAnswer(true).comment ==null\"></button></div><div class=\"user_icon\">{{getUserName(true,true)}}<div><div>{{getUserName(true,false)}}</div><img src=\"/assets/images/user_icon_arrow.png\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-section.html', "<ng-virtual><div class=\"element\"><div class=\"element_header\"><div class=\"title\" ng-bind-html=\"getTitleCode() | translate\"></div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\" ng-hide=\"$root.instanceName  === 'verification'\"><div class=\"block_status\"><div class=\"lock_status\" ng-click=\"lock()\" ng-class=\"getLockClass()\"><div ng-bind-html=\"'LOCK_BY' | translateWithVars: [getLockerName()] \" ng-show=\"getLocker() != null\"></div></div><div class=\"validate_status\" ng-click=\"valide()\" ng-class=\"getValidateClass()\"><div ng-bind-html=\"'VALIDATE_BY' | translateWithVars: [getValidatorName()] \" ng-show=\"getValidator() != null\"></div></div><div class=\"verified\" ng-click=\"isDisabled || displayVerificationRejectedMessage()\" ng-class=\"getVerificationClass(false,true)\"><div ng-bind-html=\"'VERIFIED_BY' | translateWithVars: [getVerifierName()] \" ng-show=\"getVerifier() != null\"></div></div></div></div><div class=\"element_sidebar\" ng-show=\"$root.instanceName  === 'verification'\"><div class=\"block_status\"><div class=\"verification_status_approval\" ng-click=\"isDisabled || verification(true)\" ng-class=\"getVerificationClass(true)\" ng-hide=\"isVerificationDisabled() === true\"><div ng-bind-html=\"'VERIFIED_BY' | translateWithVars: [getVerifierName()] \" ng-show=\"getVerifier() != null\"></div></div><div class=\"verification_status_reject\" ng-click=\"isDisabled || verification(false)\" ng-class=\"getVerificationClass(false)\" ng-hide=\"isVerificationDisabled()=== true\"><div ng-bind-html=\"'VERIFIED_BY' | translateWithVars: [getVerifierName()] \" ng-show=\"getVerifier() != null\"></div></div><div class=\"verified\" ng-click=\"isDisabled || displayVerificationRejectedMessage()\" ng-class=\"getVerificationClass(false,true)\" ng-show=\"isVerificationDisabled()=== true\"><div ng-bind-html=\"'VERIFIED_BY' | translateWithVars: [getVerifierName()] \" ng-show=\"getVerifier() != null\"></div></div></div></div><div class=\"element_content\"><div ng-transclude ng-class=\"getMode()\"></div></div></div></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-sub-sub-title.html', "<div><div class=\"sub_sub_title\"><div class=\"question_info\" ng-show=\"hasDescription()\"><div class=\"question_info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-bind-html=\"getQuestionCode() | translate\"></span><ng-virtual ng-transclude></ng-virtual></div></div>");$templateCache.put('$/angular/templates/mm-awac-block.html', "<ng-virtual><div ng-transclude ng-class=\"{true:'condition-false', false:''}[getCondition() === false]\"></div></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-repetition-name.html', "<div><div class=\"repetition-title\"><div class=\"question_info\" ng-show=\"hasDescription()\"><div class=\"question_info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span class=\"glyphicon glyphicon-record\"></span><span ng-bind-html=\"getQuestionCode() | translate\"></span><ng-virtual ng-transclude></ng-virtual></div></div>");$templateCache.put('$/angular/templates/mm-awac-document-question.html', "<div class=\"oneelement document-question-block document-question\"><div ng-bind-html=\"'QUESTION_FILE_UPLOAD' | translate\" ng-hide=\"getDataToCompare()==true|| getIsAggregation()===true\"></div><div class=\"document-question-progress-bar\" ng-show=\"inDownload=== true &amp;&amp; percent != 100\"><div ng-style=\"style\"><spa></spa></div></div><div class=\"document-question-progress-percentage\" ng-show=\"inDownload=== true &amp;&amp; percent != 100\">{{percent}} %</div><div ng-bind-html=\"'QUESTION_FILE_TREATEMENT' | translate\" ng-show=\"inDownload=== true &amp;&amp; percent == 100\"></div><input name=\"{{ getQuestionCode() }}\" ng-file-select=\"onFileSelect($files)\" type=\"file\" ng-hide=\"getDataToCompare()==true|| getIsAggregation()===true || inDownload === true || getDisabled() === true\"><div ng-show=\"getFileNumber()&gt;0\">{{getFileNumber()}} {{'QUESTION_FILE_COUNT_ALREADY_UPLOAD' | translateText}}</div><button class=\"button\" ng-click=\"openDocumentManager()\" ng-bind-html=\"'QUESTION_FILE_CONSULT' | translate\" type=\"button\" ng-show=\"getFileNumber()&gt;0\"></button></div>");$templateCache.put('$/angular/templates/mm-awac-real-question.html', "<input class=\"oneelement\" ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true || getDisabled() === true\" style=\"text-align:right;\" numbers-only=\"double\" name=\"{{ getQuestionCode() }}\" ng-model=\"getAnswer().value\" type=\"text\">");$templateCache.put('$/angular/templates/mm-awac-integer-question.html', "<input class=\"oneelement\" ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true || getDisabled() === true\" style=\"text-align:right;\" numbers-only=\"integer\" name=\"{{ getQuestionCode() }}\" ng-model=\"getAnswer().value\" type=\"text\">");$templateCache.put('$/angular/templates/mm-awac-repetition-question.html', "<div ng-class=\"{true:'condition-false', false:''}[getCondition() === false]\"><div class=\"repetition-question\"><div class=\"repetition-question-title\" style=\"display : inline-block; margin-right : 20px\" ng-bind-html=\"getQuestionSetCode() + '_LOOPDESC' | translate\"></div><button class=\"button remove-button\" ng-click=\"removeAnwser()\" ng-bind-html=\"'DELETE' | translate\" type=\"button\" ng-hide=\"isQuestionLocked() === true\"></button><div class=\"repetition-question-container\"><ng-virtual ng-transclude class=\"element_stack\"></ng-virtual></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-real-with-unit-question.html', "<span class=\"twoelement\"><input ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true || getDisabled() === true\" style=\"text-align:right;\" numbers-only=\"double\" name=\"{{ getQuestionCode() }}\" ng-model=\"getAnswer().value\" type=\"text\"><select ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true || getDisabled() === true\" name=\"{{ getQuestionCode() }}_UNIT\" ng-options=\"p.code as p.name for p in getUnits()\" ng-model=\"getAnswer().unitCode\"></select></span>");$templateCache.put('$/angular/templates/mm-awac-repetition-add-button.html', "<button class=\"button add-repetition-button\" ng-click=\"addIteration()\" type=\"button\" ng-hide=\"isQuestionLocked() === true\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"getQuestionSetCode() + '_LOOPDESC' | translate\"></span></button>");$templateCache.put('$/angular/templates/mm-awac-percentage-question.html', "<span class=\"twoelement\"><input ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true || getDisabled() === true\" style=\"text-align:right;\" numbers-only=\"percent\" name=\"{{ getQuestionCode() }}\" ng-model=\"getAnswer().value\" type=\"text\"><span style=\"margin-left:5px\">%</span></span>");$templateCache.put('$/angular/templates/mm-awac-string-question.html', "<input class=\"oneelement\" ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true || getDisabled() === true\" style=\"text-align:right;\" name=\"{{ getQuestionCode() }}\" ng-model=\"getAnswer().value\" type=\"text\">");$templateCache.put('$/angular/templates/mm-awac-select-question.html', "<select class=\"oneelement\" ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true || getDisabled() === true\" style=\"text-align:right;\" name=\"{{ getQuestionCode() }}\" ng-options=\"p.key as p.label for p in getOptions()\" ng-model=\"getAnswer().value\"></select>");$templateCache.put('$/angular/templates/mm-awac-boolean-question.html', "<span class=\"twoelement\"><span style=\"text-align:center\"><span style=\"vertical-align:middle;margin-right : 15px;\" ng-bind-html=\"'YES' | translate\"></span><input ng-disabled=\"getDataToCompare()===true || getIsAggregation()===true || getDisabled() === true\" style=\"width :20px !important;margin:0;vertical-align:middle;\" name=\"{{radioName}}\" value=\"1\" ng-model=\"getAnswer().value\" type=\"radio\"></span><span style=\"text-align:center\"><span style=\"vertical-align:middle;margin-right : 15px;\" ng-bind-html=\"'NO' | translate\"></span><input ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true|| getDisabled() === true\" style=\"width :20px !important;margin:0;vertical-align:middle;\" name=\"{{radioName}}\" value=\"0\" ng-model=\"getAnswer().value\" type=\"radio\"></span></span>");$templateCache.put('$/angular/templates/mm-plus-minus-toggle-button.html', "<button class=\"plus-minus\" ng-click=\"toggle()\"><div class=\"plus\" ng-hide=\"ngModel\"><span class=\"fa fa-plus\"></span></div><div class=\"minus\" ng-show=\"ngModel\"><span class=\"fa fa-minus\"></span></div></button>");$templateCache.put('$/angular/templates/mm-awac-result-legend.html', "<div class=\"chart-legend\"><!--<span>{{ ngModel.reportLines }}</span>--><div ng-show=\"(ngModel.reportLines | filter:{color: '!!'}).length == 0\"><span ng-bind-html=\"'RESULTS_LEGENDS_NODATA' | translate\"></span></div><table ng-hide=\"(ngModel.reportLines | filter:{color: '!!'}).length == 0\"><thead ng-show=\"ngModel.rightPeriod != null\"><tr><th></th><th></th><th colspan=\"2\"><span class=\"period-header\" style=\"color: {{ {false: ngModel.leftColor, true: ''}[getMode()!='numbers'] }}; border-bottom-color: {{ {false: ngModel.leftColor, true: ''}[getMode()!='numbers'] }}\">{{ngModel.leftPeriod}}</span></th><th colspan=\"2\"><span class=\"period-header\" style=\"color: {{ {false: ngModel.rightColor, true: ''}[getMode()!='numbers'] }}; border-bottom-color: {{ {false: ngModel.rightColor, true: ''}[getMode()!='numbers'] }}\">{{ngModel.rightPeriod}}</span></th></tr></thead><tbody><tr ng-repeat=\"line in ngModel.reportLines | filter:{color: '!!'}\"><td><span class=\"circled-number\" ng-show=\"getMode()=='numbers' &amp;&amp; getNumber(line) != null\">{{ getNumber(line) }}</span><span class=\"chart-legend-bullet-color\" style=\"background: {{ line.color }}\" ng-hide=\"getMode()=='numbers'\"></span></td><td><span ng-bind-html=\"line.indicatorName | translate\"></span></td><td class=\"align-right data-cell\" style=\"color: {{ {false: ngModel.leftColor, true: ''}[getMode()!='numbers'] }}\"><span>{{ (line.leftScope1Value + line.leftScope2Value + line.leftScope3Value + line.leftOutOfScopeValue) | numberToI18NRoundedOrFullIfLessThanOne }}&nbsp;tCO2e</span></td><td class=\"align-right data-cell opacity-50\" style=\"color: {{ {false: ngModel.leftColor, true: ''}[getMode()!='numbers'] }}\"><span>{{ line.leftPercentage | numberToI18N }}&nbsp;%&nbsp;</span></td><td class=\"align-right data-cell\" style=\"color: {{ {false: ngModel.rightColor, true: ''}[getMode()!='numbers'] }}\" ng-show=\"ngModel.rightPeriod != null\"><span>{{ (line.rightScope1Value + line.rightScope2Value + line.rightScope3Value + line.rightOutOfScopeValue) | numberToI18NRoundedOrFullIfLessThanOne }}&nbsp;tCO2e</span></td><td class=\"align-right data-cell opacity-50\" style=\"color: {{ {false: ngModel.rightColor, true: ''}[getMode()!='numbers'] }}\" ng-show=\"ngModel.rightPeriod != null\"><span>{{ line.rightPercentage | numberToI18N }}&nbsp;%&nbsp;</span></td></tr></tbody></table></div>");$templateCache.put('$/angular/templates/mm-awac-municipality-menu.html', "<div class=\"nav_tabs\" ng-show=\"displayMenu===true\"><div class=\"nav_entreprise\"><div class=\"site_menu\"><div class=\"menu menu_municipality\"><button class=\"button\" ng-click=\"navTo('/form/TAB_C1')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_C1') == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB_C1' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_C1')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-click=\"navTo('/form/TAB_C2')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_C2') == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB_C2' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_C2')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-click=\"navTo('/form/TAB_C3')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_C3')  == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB_C3' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_C3')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-click=\"navTo('/form/TAB_C4')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_C4') == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB_C4' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_C4')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-click=\"navTo('/form/TAB_C5')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_C5') == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB_C5' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_C5')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button></div></div><div class=\"last_menu\"><button class=\"button\" ng-click=\"navTo('/results')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/results') == true}\"><div class=\"tab-title\" ng-bind-html=\"'SURVEY_INTERFACE_RESULT' | translate\"></div></button><button class=\"button\" ng-click=\"isDisabled || navTo('/actions')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/actions') == true}\" ng-show=\"$root.instanceName != 'verification'\"><div class=\"tab-title\" ng-bind-html=\"'SURVEY_INTERFACE_REDUCTION' | translate\"></div></button></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-enterprise-menu.html', "<div class=\"nav_tabs\" ng-show=\"displayMenu===true\"><div class=\"nav_entreprise\"><div class=\"site_menu\"><div class=\"menu menu_enterprise\"><button class=\"button\" ng-click=\"navTo('/form/TAB2')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB2') == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB2' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB2')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-click=\"navTo('/form/TAB3')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB3')  == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB3' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB3')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-click=\"navTo('/form/TAB4')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB4') == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB4' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB4')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-click=\"navTo('/form/TAB5')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB5') == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB5' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB5')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-click=\"navTo('/form/TAB6')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB6') == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB6' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB6')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-click=\"navTo('/form/TAB7')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB7') == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB7' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB7')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button></div></div><div class=\"last_menu\"><button class=\"button\" ng-click=\"navTo('/results')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/results') == true}\"><div class=\"tab-title\" ng-bind-html=\"'SURVEY_INTERFACE_RESULT' | translate\"></div></button><button class=\"button\" ng-click=\"isDisabled || navTo('/actions')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/actions') == true}\" ng-show=\"$root.instanceName != 'verification'\"><div class=\"tab-title\" ng-bind-html=\"'SURVEY_INTERFACE_REDUCTION' | translate\"></div></button></div></div></div>");});