
new Vue({
  el: '#resReload',
  data: {
	  dataReload: true,
	  fileReload: false,
	  selectValue: '',
	  tableList: '',
	  fileList: '',
  },
  methods:{
	  reload: function(){
		if(this.dataReload){
			alert("dataReload！");
		} else {
			alert("登陆成功！");
			window.location.href="./Manager.html";
		}	
	}
  }
});
