CKEDITOR.plugins.add("autobase64", {
    lang: ["af", "ar", "bg", "bn", "bs", "ca", "cs", "cy", "da", "de", "el", "en", "en-au", "en-ca", "en-gb", "eo", "es", "et", "eu", "fa", "fi", "fo", "fr", "fr-ca", "gl", "gu", "he", "hi", "hr", "hu", "id", "is", "it", "ja", "ka", "km", "ko", "ku", "lt", "lv", "mk", "mn", "ms", "nb", "nl", "no", "pl", "pt", "pt-br", "ro", "ru", "si", "sk", "sl", "sq", "sr", "sr-latn", "sv", "th", "tr", "ug", "uk", "vi", "zh", "zh-cn"],
    requires: "dialog",
    icons: "autobase64",
    hidpi: true,
    init: function (editor) {
        var pluginName = 'autobase64';

        editor.on("paste", function (evt) {
            console.log(evt);

            var $elem = $(evt.data.dataValue).eq(0);
            if ($elem[0].tagName == 'IMG') {

                evt.cancel();

            }

        });

    }
});