<table id="errorGrid" dojoType="dojox.grid.EnhancedGrid" query="{id: '*'}" plugins="{ filter:false}" 
		store="errorGridStore" style="width:85%;height:30%;top:14%;position:relative">
	<thead>
		<tr>
			<th field="source" width="20%">Error Source</th>
			<th field="code" width="10%">Error Code</th>
			<th field="description" width="60%">Description</th>
			<th field="severity" width="10%">Severity</th>
		</tr>
	</thead>
</table>
