<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>AdminLTE 3 | Lockscreen</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Font Awesome Icons -->
    <link rel="stylesheet" href="<c:out value="resources/plugins/fontawesome-free/css/all.min.css"/>">
    <!-- Theme style -->
    <link rel="stylesheet" href="<c:out value="resources/dist/css/adminlte.min.css"/>">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Google Font: Source Sans Pro -->
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
</head>
<body class="hold-transition lockscreen">
<!-- Automatic element centering -->
<div class="lockscreen-wrapper">
    <div class="lockscreen-logo">
        <a href="/"><b>WEB</b>Scraping</a>
    </div>
    <!-- User name -->
    <div class="lockscreen-name">Choisir une sous catégorie :</div>

    <!-- START LOCK SCREEN ITEM -->
    <div class="lockscreen-item">

        <!-- lockscreen credentials (contains the form) -->
        <form action="<c:out value="sous-categorie/produits"/>" method="post" style="display: flex; justify-content: center;">
            <select class="custom-select my-1 mr-sm-2" id="inlineFormCustomSelectPref" name="id_souscat">
                <option selected>Choisir...</option>
                <c:forEach items="${souscatlist}" var="souscat">
                    <option value="${souscat.id}">${souscat.nom}</option>
                </c:forEach>
            </select>

            <button type="submit" class="btn btn-primary my-1">Scraper</button>
        </form>
        <!-- /.lockscreen credentials -->

    </div>
    <div class="lockscreen-footer text-center">
        Copyright &copy; 2019-2020 <b><a href="http://localhost:8080/WebScraping_war/" class="text-black">WebScraping</a></b><br>
        Tous les droits sont résérvés
    </div>
</div>
<!-- /.center -->

<!-- jQuery -->
<script src="<c:out value="resources/plugins/jquery/jquery.min.js"/>"></script>
<!-- Bootstrap 4 -->
<script src="<c:out value="resources/plugins/bootstrap/js/bootstrap.bundle.min.js"/>"></script>
</body>
</html>
