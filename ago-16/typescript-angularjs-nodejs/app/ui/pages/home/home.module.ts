/// <reference path="../../../../typings/index.d.ts" />

namespace UI.Pages.Feature1 {
    angular
        .module('app.ui.pages.home', [
            'app.ui.components'
        ])
        .controller('HomeController', UI.Pages.Home.HomeController)
        .directive('chatRoomWindow', () => new UI.Pages.Home.ChatRoomWindow());
}