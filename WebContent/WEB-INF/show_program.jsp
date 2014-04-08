<%@ page pageEncoding="UTF-8" %>
<%@ page import ="java.util.List" %>
<%@ page import ="modele.Spectacle" %>
<%@ page import ="exceptions.SpectacleException" %>
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
					<h1>Liste des programmes</h1>
				</div>
				
				<%
				SpectacleException excep = (SpectacleException) request.getAttribute("erreur");
				List<Spectacle> spectacles = (List<Spectacle>) request.getAttribute("spectacles");
				if (excep != null) {
				%>
					<div class="alert alert-warning">
                	<h4>Une erreur a été rencontrée</h4>
                	<p>
                		<% out.print(excep.getMessage()); %><br />
                		<a href="">Veuillez essayer de recharger la page</a>
                	</p>
               		</div>
				<%
				} else {
					if(spectacles == null || spectacles.size() == 0) {
					%>
					<p>Il n'y a pas de spectacle actuellement</p>
					<%
					} else {
						%>
						<table class="table table-striped table-hover">
							<thead>
								<th>Nom du spectacle</th>
								<th>Représentation</th>
							</thead>
							<tbody>
								<%
								for(Spectacle spect : spectacles) {
									%>
									<tr>
										<td><% out.print(spect.getNom()); %></td>
										<td><a href="RepresentationSpectacleServlet?numS=<% out.print(spect.getId()); %>">Voir les représentations</a></td>
									</tr>
									<%
								}
								%>
							</tbody>
						</table>
						<%
					}
				}
				%>
			</div>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="/assets/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>