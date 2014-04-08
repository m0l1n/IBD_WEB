<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Petit Théatre de l'Informatique</title>

<link href="/assets/bootstrap/css/bootstrap-theatre.min.css"
	rel="stylesheet">
<!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
</head>
<body>
	<nav class="navbar navbar-default" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Cacher/Afficher la navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Petit Théatre de l'Informatique</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="/">Accueil</a></li>
					<li class="active"><a href="#">Programme</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/admin/admin.html">Administration</a></li>
				</ul>
			</div>
		</div>
	</nav>
	
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<div class="page-header">
					<h1>Chercher une représentation</h1>
				</div>
				<%
				String erreurMessage = (String) request.getAttribute("erreurMessage");
				if (erreurMessage != null) {
				%>
					<div class="alert alert-warning">
                	<h4>Une erreur a été rencontrée</h4>
                	<p>
                		<% out.print(erreurMessage); %><br />
                	</p>
               		</div>
				<%
				}
				%>
				<form role="form" action="" method="post">
					<div class="form-group">
						<label for="numS">Numéro du spectacle : </label>
						<input type="number" class="form-control" name="numS" id="numS" placeholder="Renseignez le numéro du spectacle">
					</div>
					<button type="submit" class="btn btn-default">Envoyer</button>
				</form>
			</div>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="/assets/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>