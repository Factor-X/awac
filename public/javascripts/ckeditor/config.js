/**
 * @license Copyright (c) 2003-2014, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function (config) {
    // Changes to default configuration.
    // For complete reference see:
    // http://docs.ckeditor.com/#!/api/CKEDITOR.config

    // Defines Full & Basic toolbars
    config.toolbar_Basic = [
        { name: 'basicstyles', items: [ 'Bold', 'Italic', 'Underline' ] },
        { name: 'paragraph', items: [ 'BulletedList', 'NumberedList', '-', 'Outdent', 'Indent' ] },
        { name: 'links', items: [ 'Link', 'Unlink' ] },
        { name: 'clipboard', items: [ 'PasteFromWord' ] }
    ];

    // Load toolbar_Name where Name = Basic or Full
    config.toolbar = 'Basic';

    // Remove 'advanced' tab, and hide 'target' tab, from 'link' dialog window
    config.removeDialogTabs = 'link:advanced';
    config.linkShowTargetTab = false;

};

// Customize "Link" dialog window
CKEDITOR.on('dialogDefinition', function (ev) {
    var dialogName = ev.data.name;

    if (dialogName == 'link') {
        var dialogDefinition = ev.data.definition;

        // (a trick to work around the double triggering of this event)
        if (dialogDefinition.updated === true) {
            return;
        }
        dialogDefinition.updated = true;

        // reduce height
        dialogDefinition['minHeight'] = 150;

        // set target type = '_blank'
        var targetTab = dialogDefinition.getContents('target');
        var targetField = targetTab.get('linkTargetType');
        targetField['default'] = '_blank';

        // remove 'anchor' link type option
        var infoTab = dialogDefinition.getContents('info');
        var linkTypeField = infoTab.get('linkType');
        linkTypeField['items'].splice(1, 1);

        // remove all protocol options excepted http/s
        var protocolField = infoTab.get('protocol');
        protocolField['items'].splice(2);
    }

    return;
});
