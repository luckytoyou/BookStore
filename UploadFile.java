
//文件上传步骤：
//1.创建磁盘内置文件DiskFileItemFactory
//2.创建文件上传解析器ServletFileUpload
//3.判断提交数据是否是上传表单的数据
//4.ServletFileUpload 解析器解析上传数据，解析结果返回一个List 集合，每个FileItem对应一个Form表单输入项



private void addBook(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);//判断提交数据是否是上传表单的数据
		if(!isMultipart)
			throw new RuntimeException("你的表单enctype属性必须是multipart/form-data类型的");
		DiskFileItemFactory factory = new DiskFileItemFactory();//创建磁盘内置文件
		ServletFileUpload sfu = new ServletFileUpload(factory);//创建文件上传解析器
		try {
			List<FileItem> items = sfu.parseRequest(request);//ServletFileUpload 解析器解析上传数据，解析结果返回一个List 集合，每个FileItem对应一个Form表单输入项
			Book book = new Book();
			for(FileItem item:items){
				if(item.isFormField()){
					processFormField(item,book);
				}else{
					processUpload(item,book);
				}
			}
			s.addBook(book);
			response.getWriter().write("<script type='text/javascript'>alert('添加成功')</script>");
			response.setHeader("Refresh", "0;URL="+request.getContextPath()+"/servlet/ControllerServlet?op=addBookUI");
		} catch (FileUploadException e) {
			throw new RuntimeException("解析上传请求失败");
		}
	}





