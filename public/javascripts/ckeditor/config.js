/**
 * @license Copyright (c) 2003-2014, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function (config) {

    // %REMOVE_START%
    // The configuration options below are needed when running CKEditor from source files.
    config.plugins = 'dialogui,dialog,about,a11yhelp,basicstyles,blockquote,clipboard,panel,floatpanel,menu,contextmenu,resize,button,toolbar,elementspath,enterkey,entities,popup,filebrowser,floatingspace,listblock,richcombo,format,horizontalrule,htmlwriter,wysiwygarea,image,indent,indentlist,fakeobjects,link,list,magicline,maximize,pastetext,pastefromword,removeformat,showborders,sourcearea,specialchar,menubutton,scayt,stylescombo,tab,table,tabletools,undo,wsc,base64image';
    config.skin = 'moono';
    // %REMOVE_END%

    // Define changes to default configuration here.
    // For complete reference see:
    // http://docs.ckeditor.com/#!/api/CKEDITOR.config

    // The toolbar groups arrangement, optimized for two toolbar rows.
    config.toolbarGroups = [
        { name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
        { name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ] },
        { name: 'links' },
        { name: 'insert' },
        { name: 'forms' },
        { name: 'tools' },
        { name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
        { name: 'others' },
        '/',
        { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
        { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
        { name: 'styles' },
        { name: 'colors' },
        { name: 'about' }
    ];

    config.toolbar_Basic = [
        { name: 'basicstyles', items: [ 'Bold', 'Italic', 'Underline' ] },
        { name: 'paragraph', items: [ 'BulletedList', 'NumberedList', '-', 'Outdent', 'Indent' ] },
        { name: 'links', items: [ 'Link', 'Unlink' ] },
        { name: 'clipboard', items: [ 'PasteFromWord' ] }
    ];

    config.toolbar_Wysisyg = [
        {"name": "basicstyles", "items": ["Bold", "Italic", "Strike", "Underline"]},
        {"name": "paragraph", "items": ["BulletedList", "NumberedList", "Blockquote"]},
        {"name": "editing", "items": ["JustifyLeft", "JustifyCenter", "JustifyRight", "JustifyBlock"]},
        {"name": "links", "items": ["Link", "Unlink"]},
        {"name": "tools", "items": ["Maximize"]},
        {"name": "styles", "items": ["Format", "FontSize", "TextColor", "PasteText", "RemoveFormat"]},
        {"name": "insert", "items": ["Table", "SpecialChar", "base64image"]},
        {"name": "forms", "items": ["Outdent", "Indent"]}
        /* {"name": "document", "items": ["PageBreak" , "Source" ]} */
    ];


    // Remove some buttons provided by the standard plugins, which are
    // not needed in the Standard(s) toolbar.
    config.removeButtons = 'Underline,Subscript,Superscript';

    // Set the most common block elements.
    config.format_tags = 'p;h1;h2;h3;pre';

    // Simplify the dialog windows.
    config.removeDialogTabs = 'image:advanced;link:advanced';
};

