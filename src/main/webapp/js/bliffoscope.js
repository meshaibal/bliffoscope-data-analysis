(function(){
	//This creates angular app
	var bliffoscopeApp = angular.module('bliffoscopeApp', []);
	
	// This is a angular directive to upload file
	bliffoscopeApp.directive('fileData',['$parse', function($parse){
		return {
			restrict : 'A' ,
			link : function(scope, element, attrs){
				var model = $parse(attrs.fileData);
				var modelSetter = model.assign;
				
				element.bind('change', function(){
					scope.$apply(function(){
						modelSetter(scope, element[0].files[0]);
					});
				});
			}
		}
	}]);
	
	// This angular service has two functions. 1st to upload file, get search results and second to download result plottings
	bliffoscopeApp.service('bliffoscopeFileUploadService', ['$http', function($http){
		this.fileUpload = function(bliffoscopeDataArray, uploadUrl){
			
			var formData = new FormData();
			
			var bliffoscopeObj ;
			for(var i=0; i<bliffoscopeDataArray.length-1; i++){
				bliffoscopeObj = bliffoscopeDataArray[i];
				formData.append('sourceFile'+(i+1), bliffoscopeObj.getFile());
				formData.append('sourcePixelHeight'+(i+1), bliffoscopeObj.getFilePixelHeight());
				formData.append('type'+(i+1), bliffoscopeObj.getFileType());
			}
			
			bliffoscopeObj = bliffoscopeDataArray[2];
			formData.append('targetFile', bliffoscopeObj.getFile());
			formData.append('targetPixelHeight', bliffoscopeObj.getFilePixelHeight());
			formData.append('type', bliffoscopeObj.getFileType());
			
			$('.btn-analyze').attr('disabled','disabled');
			$http.post(uploadUrl, formData, {
				transformRequest: angular.identity,
	            headers: {'Content-Type': undefined}
			}).then(
					function success(response){
						// Incase of any error is thrown from JAVA controller. This error is handled and message are sent to $http response.
						if(response.data.isError === true){
							$('button.btn-analyze').removeAttr('disabled');
							$('div.result-error').html(response.data.ErrorMessage);
							$('div.result-error').show();
							return;
						}
						jqueryDatatable.loadDataInTable(response.data);
						
	        },
	        		function error(response){
	        			$('div.result-error').html('<strong>Error !</strong> Something went wrong !');
	        			$('div.result-error').show();
	        });
		},
		
		this.downloadResult = function(){
			var fileUrl = contextPath+'/target-finder/download/'+$('#resultPlotting').attr('url');
			
			$http.get(fileUrl)
			     .then(function success(response){
			    	 var anchor = angular.element('<a/>');
			         anchor.attr({
			             href: 'data:attachment/txt;charset=utf-8,' + encodeURI(response.data),
			             target: '_blank',
			             download: $('#resultPlotting').attr('url')
			         })[0].click();
			     }, function error(response){
			    	 console.log("error");
			     });
		}
	}]);
	
	// This is the angular controller. One controller is used in this application
	bliffoscopeApp.controller('bliffoscopeController',['$scope', 'bliffoscopeFileUploadService', function($scope, bliffoscopeFileUploadService){
		
		$scope.noOfSource = 1;
		$scope.showResult = false;
		
		$('div.result-error').hide();
		$('div.download-link').hide();
		
		$scope.uploadFile = function(){
			
			$('div.result-error').hide();
			$scope.fileNotUploaded = false;
			$scope.showResult = false;
			
			if($scope.source1File == undefined || $scope.source2File == undefined || $scope.targetFile == undefined){
				$scope.fileNotUploaded = true;
				return;
			}
            
            var bliffoscopeDataArray = [];
            
            bliffoscopeDataArray.push(createBliffoscopeObject($scope.source1File, $scope.source1VerticalLength, $scope.soureType1));
            bliffoscopeDataArray.push(createBliffoscopeObject($scope.source2File, $scope.source2VerticalLength, $scope.soureType2));
            bliffoscopeDataArray.push(createBliffoscopeObject($scope.targetFile, $scope.targetVerticalLength, "TARGET_DATA"));
            
            var uploadUrl = contextPath+"/target-finder/find-target";
            
            bliffoscopeFileUploadService.fileUpload(bliffoscopeDataArray, uploadUrl);
            $scope.showResult = true;
		};
		
		$scope.download = function(){
			bliffoscopeFileUploadService.downloadResult();
		}
	}]);
	
	// This is a angular directive. It serves template for datatable to display results.
	bliffoscopeApp.directive('showResult', function(){
		return{
			templateUrl : 'js/templates/show-result.htm'
		}
	});
	
	// This function helps to create Bliffoscope obje with data
	function createBliffoscopeObject(file, filePixelHeight, fileType){
		var bliffoscopeObject = new BliffoscopeData();
		bliffoscopeObject.setFile(file);
		bliffoscopeObject.setFilePixelHeight(filePixelHeight);
		bliffoscopeObject.setFileType(fileType);
		
		return bliffoscopeObject;
	}
	
	// This is Bliffoscope object
	function BliffoscopeData(){
		
		var file;
		var filePixelHeight;
		var fileType;
		
		this.setFile = function(file){
			this.file = file;
		};
		this.setFilePixelHeight = function(filePixelHeight){
			this.filePixelHeight = filePixelHeight;
		};
		this.setFileType = function(fileType){
			this.fileType = fileType;
		}
		
		this.getFile = function(){
			return this.file;
		};
		this.getFilePixelHeight = function(){
			return this.filePixelHeight;
		};
		this.getFileType = function(){
			return this.fileType;
		}
	}
})();


// This is outside earlier namespace. This fill datatable with data
var jqueryDatatable = (function(){
	
	var loadDataInTable = function(data){
		
		$('a#resultPlotting').attr('url', data.resultPlottingUrl);
		if ($.fn.DataTable.isDataTable('#tableResultList')) {
			$('#tableResultList').dataTable().fnDestroy();
		}
		var dataTable = $('#tableResultList').DataTable({
			"data" : data.results,
			"bFilter": false,
			"columns": [
			            { "data": "objectType" },
			            { "data": "matchPercentage" },
			            { "data": "corordinateText" }
			        ],
			"columnDefs": []
		});
		$('div.download-link').show();
		$('button.btn-analyze').removeAttr('disabled');
	};
	
	return {
		// this makes this funtion accssible to outside of this scope
		loadDataInTable : loadDataInTable 
	}
})();


