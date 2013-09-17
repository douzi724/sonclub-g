<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title><g:layoutTitle default="SonClub"/></title>

        <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
        <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
        <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
        <r:require modules="bootstrap"/>
        <g:layoutHead/>
        <r:layoutResources/>

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
            <g:javascript src="html5shiv.js" />
            <g:javascript src="respond.min.js" />
        <![endif]-->
    </head>
    <body class="bg-img">
        <div class="container">
            <g:layoutBody/>
        </div>
        <g:javascript library="application"/>
        <r:layoutResources/>
    </body>
</html>