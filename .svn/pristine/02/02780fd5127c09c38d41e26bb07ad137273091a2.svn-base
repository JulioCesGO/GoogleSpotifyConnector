<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>Exemplo</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
	<script
		  src="https://code.jquery.com/jquery-1.12.4.min.js"
		  integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
		  crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
</head>
<body>
<div class="content">

	<div class="container border  border-danger mt-5 mb-5">
		
	<c:if test="${ empty userProfile  }">
		
		<div class="row mt-5 ">
			<div class="col"></div>
			<div class="col col-md-auto text-center">
				<h5>Exibir Informações do seu Usuário</h5>			
				<img alt="" src="/resources/google.jpg" class="mt-3 mb-3">			
				<form action="logarGoogle">
					<button class="btn btn-primary center-block">Logar no Google</button>
				</form>
			</div>
			<div class="col"></div>
		</div>
		<div class="row mt-5">
		
			<div class="col-12 ">
				${googleerror } 
			</div>					
		</div>
				
	</c:if>
			
	<c:if test="${not empty userProfile  }">
		
		<div class="row">
			<div class="col text-center">
				<img alt="" src="/resources/google.jpg" class="mt-3 mb-3"><br>
				<a class="btn btn-danger btn-sm" href="/Google/logout">Desconectar do Google</a>				
			</div>
		</div>
			
		<div class="row mt-5 text-center">
			<div class="col ">
				 <h5>Informação sobre o seu Usuário</h5>					
			</div>				
		</div>
		<div class="row mt-5 mb-5">
			<div class="col hidden-sm-up"></div>
			<div class="col-md-auto col-sm-12">
			<div class="media ">
			  <img class="mr-3 align-self-center  rounded-circle" src="${userProfile.picture }" alt="" width="64" height="64"/>					  
			  <div class="media-body">
			   <h5 class="mt-0 mb-1 "><a href="${userProfile.link }">${userProfile.name }</a></h5>
				Id: ${userProfile.id }<br>
				Locale: ${userProfile.locale }
			  </div>
			</div>
			</div>
			<div class="col hidden-sm-up"></div>
		</div>
	
		
	</c:if>
	</div>
	<div class="container mt-5 mb-5 border border-success" >
	<c:if test="${empty spotify  }">
		<div class="row mt-5 ">
			<div class="col  text-center">
				<h5>Exibir informações sobre suas Músicas</h5>
				<img alt="" src="/resources/spotify.jpg"  class="mt-3 mb-3"/>
				<form action="logarSpotify">
					<button  class="btn btn-primary center-block"  >Logar no Spotify</button>
				</form>
			</div>
		</div>
		<div class="row mt-5">
			<div class="col-12 ">
				${spotifyerror } 
			</div>					
		</div>
	</c:if>
	
	
	<c:if test="${not empty spotify  }">
		<div ng-app="myApp" ng-controller="myCtrl">
		
			
				<div class="row mt-5">
					<div class="col text-center">
						<p><img alt="" src="/resources/spotify.jpg" class="mt-3"></p>
						<a class="btn btn-danger btn-sm" href="/Spotify/logout">Desconectar do Spotify</a>
					</div>
				</div>
			
				<div class="row mt-5">
					<div class="col text-center">
						<h5>Veja abaixo o que esta sendo reproduzindo atualmente(BETA)</h5>
					</div>			
	  
				</div>
				<div class="row mt-5" ng-if="!errorCurrentUsersSavedTracks">
					<div class="col hidden-sm-up"></div>
					<div class="col-md-auto col-sm-12">
						<div class="media">
						  <img class="mr-3 align-self-center" ng-repeat="image in currentPlayingTrack.item.album.images" ng-if="image.width < 80 " src="{{image.url }}"/>					  
						  <div class="media-body">
						   <h5 class="mt-0 mb-1 ">{{currentPlayingTrack.item.name}}</h5>
							Artista: <span ng-repeat="artist in currentPlayingTrack.item.artists">{{artist.name}}</span><br>
							Album: {{currentPlayingTrack.item.album.name}}<br>
							<button ng-click="atualizaTrac()" class="btn btn-success">Atualizar</button>
						  </div>
						</div>
					</div>
					<div class="col hidden-sm-up"></div>
				</div>
				<div class="row mt-5" ng-if="errorCurrentUsersSavedTracks">
					<div class="col-12">
						{{errorCurrentUsersSavedTracks}}	
					</div>
					
				</div>
				<div class="row mt-5">
					<div class="col text-center">
						<h5>Veja as 20 ultimas musicas adicionadas por você</h5>
					</div>
				</div>
				<div class="row mt-5 mb-5" ng-if="errorCurrentUsersSavedTracks">
					<div class="col-12 ">
						{{errorCurrentUsersSavedTracks}}
					</div>					
				</div>
				<div class="row mt-5" ng-if="!errorCurrentUsersSavedTracks">
					<div class="col hidden-sm-up"></div>
					<div class="col-md-auto col-sm-12">
						<ul class="list-unstyled"  ng-repeat="item in currentListTrack.items">
							<li class="media mt-2">
								<img class="mr-3 align-self-center" ng-repeat="image in item.track.album.images" ng-if="image.width < 80 " src="{{image.url }}"/>
								<div class="media-body">
									<h5 class="mt-0 mb-1 ">{{item.track.name}}</h5>
									Artista: <span ng-repeat="artist in item.track.artists">{{artist.name}}</span><br>
									Album: {{item.track.album.name}}<br>
									<button ng-click="play(item.track.uri)"  class="btn btn-success">Play</button>
								</div>
								
			    			</li>	
							
						</ul>
					</div>
					<div class="col hidden-sm-up"></div>
				</div>	
				<div class="row mt-5 mb-5" ng-if="!errorCurrentUsersSavedTracks">
					<div class="col text-center">
						<a class="btn btn-danger btn-sm" href="/Spotify/logout">Desconectar do Spotify</a>
					</div>
				</div>
					
			</div>	
	</c:if>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
	
	<script type="text/javascript">
	var app = angular.module('myApp', []);
	app.controller('myCtrl', function($scope,$http,$log,$timeout) {
		
		$scope.time = 0;
		$http({
	        method : "GET",
	        url : "/Spotify/getCurrentUsersSavedTracks"
	    }).then(function mySuccess(response) {
	        if (response.data.currentListTrack != null )
	        {
	        	$scope.currentListTrack = response.data['currentListTrack'];
	        }
	        else
	        {
	        	$log.log(response.data.error)
	        	$scope.errorCurrentUsersSavedTracks = response.data.error;
	        }
	        	
	    });
		
		getCurrentPlayingTrack = function()
		{
			$http({
		        method : "GET",
		        url : "/Spotify/getCurrentPlayingTrack"
		    }).then(function mySuccess(response) {
		    	if (response.data['currentPlayingTrack'] != null )
		    	{
		    		$scope.currentPlayingTrack = response.data['currentPlayingTrack'];
		    		var valor = $scope.currentPlayingTrack.item.duration_ms - $scope.currentPlayingTrack.progress_ms;	
					$log.log(new Date() + ': tempo restante para reiniciar a pesquisa ' + valor );
					if ($scope.time - valor == 0 )
						$scope.time = 60000;
					else
						$scope.time = valor;
					$timeout(getCurrentPlayingTrack, $scope.time);
		    	}		        	
		        else
		        {
		        	$log.log(response.data['error'])
		        	$scope.errorCurrentPlayingTrack = response.data['error'];
		        }		        	
		    });
				
		}
		getCurrentPlayingTrack();
		
		//$timeout(getCurrentPlayingTrack,$scope.time);
			
		$scope.play = function(music)
		{
			$log.log(music);
			$http({
		        method : "GET",
		        url : "/Spotify/play?uri="+music
		        
		    }).then(function mySuccess(response) {
		    	$log.log(response.data);
		    	getCurrentPlayingTrack();
		    });
		}
		
		$scope.atualizaTrac = function()
		{
			getCurrentPlayingTrack()
		}
	    
	    
	});
	
	</script>
</div>
</body>
</html>
