		 
		<g:showArticles articleList="${articleRecommendationList}" times="3">
		<g:link controller="article" action="display" id="${it}"> ${message(code: 'welcome.showmore')}</g:link><BR>
		</g:showArticles>