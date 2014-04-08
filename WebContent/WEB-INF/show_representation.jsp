<%@ page pageEncoding="UTF-8" %>
<%@ page import ="java.util.List" %>
<%@ page import ="modele.Spectacle" %>
<%@ page import ="modele.Representation" %>
<%@ page import ="java.text.SimpleDateFormat" %>
<%@ page import ="java.text.DateFormat" %>
<%@ page import ="java.net.URLEncoder" %>
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
	
	<%
	Spectacle spectacle = (Spectacle) request.getAttribute("spectacle");
	List<Representation> representations = (List<Representation>) request.getAttribute("representations");
	DateFormat presentationDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	%>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<div class="page-header">
					<h1>Liste des représentations pour le spectacle <% out.print(spectacle.getNom()); %></h1>
				</div>
				<%
				if (representations == null || representations.size() == 0) {
					%>
					<p>il n'y a pas de représentation pour le spectacle</p>
					<%
				} else {
					%>
					<table class="table table-striped table-hover">
						<thead>
							<tr>
								<th>Date de la représentation</th>
								<th>Réserver une place</th>
							</tr>
						</thead>
						<tbody>
							<%
								for(Representation representation : representations) {
									String reprDate = presentationDate.format(representation.getDate());
									%>
										<tr>
											<td><% out.print(reprDate); %></td>
											<td><a href="BookPlaceServlet?numS=<% out.print(spectacle.getId()); %>&dateRep=<% out.print(URLEncoder.encode(reprDate, "UTF-8")); %>">Réserver une place</a></td>
										</tr>
									<%
								}
							%>
						</tbody>
					</table>
					<%
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