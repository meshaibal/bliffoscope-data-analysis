<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="sources" scope="request" type="java.util.List<java.lang.String>" />

<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" >
<link rel="stylesheet" type="text/css" href="css/jquery.dataTables.min.css"> 
<link rel="stylesheet" type="text/css" href="css/bliffoscope.css">  

<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" charset="utf8" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/angular.min.js"></script>

<script>var contextPath = "<%=request.getContextPath()%>"</script>
<script type="text/javascript" src="js/bliffoscope.js"></script>

<title>Insert title here</title>

</head>

<body>

<div class="container main-div" ng-app="bliffoscopeApp">
	<div ng-controller="bliffoscopeController">
		
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h2 class="panel-title">Bliffoscope Data Analysis</h2>
			</div>
			<div class="panel panel-info">
				<div class="panel-body panel-instruction-body">
					<p>
						This application accepts two files as input. These two sources files are SlimeTorpedp and Star-ship. Along with that it accepts test data. It Then finds objects
						in source files in test data.
						<br/><br/>
						Threshold match is assumed as 73% (based on some test results) 
					</p>
						
				</div>
			</div>
			<div class="panel-heading">
				<h3 class="panel-title">Upload Files</h3>
			</div>
			<form name="frmBliffoscope" class="form-inline" novalidate>
			<div class="panel-body">
				<fieldset class="source-border">
					<legend class="source-border">Source 1</legend>
					<div class="row" >
						<div class="form-group col-md-4">
							<label>Type</label>
							<select name="selSourceType1" class="custom-select input-sm" ng-model="soureType1" required >
							  <option >Select Type</option>
							  <c:forEach  items="${sources}" var="current">
							  		<option value="${current}"><c:out value="${current}"></c:out> </option>
							  </c:forEach>							  
							</select>
														
						</div>
						<div class="col-md-4 form-group" ng-class="{'has-error' : !frmBliffoscope.source1VerticalLength.$valid && !frmBliffoscope.source1VerticalLength.$pristine}">
							<label class="form-check-label">Source Height</label>
							<input type="number" min="0" name="source1VerticalLength" ng-model="source1VerticalLength" class="form-control input-sm" size="" ng-required="true"/>
							<p ng-show="!frmBliffoscope.source1VerticalLength.$valid && !frmBliffoscope.source1VerticalLength.$pristine" class="help-block with-errors">This is required !</p>
						</div>
						<div class="col-md-4">
							<label class="custom-file">Source File
					  		<input type="file" id="file"  file-data="source1File" required class="custom-file-input">  		
					  		<span class="custom-file-control"></span>
							</label>
						</div>
					</div>
				</fieldset>
				<fieldset class="source-border" >
					<legend class="source-border">Source 2</legend>
					<div class="row" >
						<div class="form-group col-md-4">
							<label>Type</label>
							<select class="custom-select input-sm" ng-model="soureType2" required>
							  <option >Select Type</option>
							  <c:forEach  items="${sources}" var="current">
							  		<option value="${current}"><c:out value="${current}"></c:out> </option>
							  </c:forEach>
							</select>
							
						</div>
						<div class="col-md-4 form-group" ng-class="{'has-error' : !frmBliffoscope.source2VerticalLength.$valid && !frmBliffoscope.source2VerticalLength.$pristine}">
							<label class="form-check-label">Source Height</label>
							<input type="number" min="0" name="source2VerticalLength" ng-model="source2VerticalLength" class="form-control input-sm" size="" required/>
							<p ng-show="!frmBliffoscope.source2VerticalLength.$valid && !frmBliffoscope.source2VerticalLength.$pristine" class="help-block with-errors">This is required !</p>
						</div>
						<div class="col-md-4">
							<label class="custom-file">Source File
					  		<input type="file" id="file"  file-data="source2File" required>  		
					  		<span class="custom-file-control"></span>
							</label>
						</div>
					</div>
				</fieldset>
				<fieldset class="source-border">
					<legend class="source-border">Target</legend>
					<div class="form-inline row" >
						<div class="col-md-4">
						</div>
						<div class="col-md-4" ng-class="{'has-error' : !frmBliffoscope.targetVerticalLength.$valid && !frmBliffoscope.targetVerticalLength.$pristine}">
							<label>Target Height</label>
							<input type="number" min="0" ng-model="targetVerticalLength" name="targetVerticalLength" class="form-control input-sm" required/>
							<p ng-show="!frmBliffoscope.targetVerticalLength.$valid && !frmBliffoscope.targetVerticalLength.$pristine" class="help-block with-errors">This is required !</p>
						</div>
						<div class="col-md-4">
							<label class="custom-file">Target File
					  		<input type="file" id="file2" file-data="targetFile" required>
					  		<span class="custom-file-control"></span>
							</label>
						</div>
					</div>
				</fieldset>
				<button class="btn btn-primary btn-analyze" ng-disabled="!frmBliffoscope.$valid" ng-click = "uploadFile()">Analyze</button>

			</div>
			</form>
			<div class="alert alert-danger" ng-show="fileNotUploaded">
				<strong>Required !</strong> All the files are required !
			</div>
			<div class="alert alert-danger result-error" >
				<strong>Error !</strong> Something went wrong !
			</div>
			<div class="download-link">
				<a id="resultPlotting" ng-click="download()" href="#" class="btn btn-info btn-md">Download result Plotting</a>
			</div>
			<div ng-show="showResult">
				
				<show-result></show-result>
			</div>
		</div>
		
	</div>
</div>


</body>