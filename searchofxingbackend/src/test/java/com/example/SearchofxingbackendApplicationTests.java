//package com.example;
//
//import cn.hutool.http.HttpRequest;
//import cn.hutool.json.JSONArray;
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//import com.example.mapper.PostMapper;
//import com.example.mapper.UserMapper;
//import com.example.pojo.Picture;
//import com.example.pojo.Post;
//import com.example.pojo.User;
//import lombok.extern.slf4j.Slf4j;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//import java.io.IOException;
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.security.cert.X509Certificate;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
////@SpringBootTest
//@Slf4j
//class SearchofxingbackendApplicationTests {
//
//	@Autowired
//	private UserMapper userMapper;
//
//
//	@Autowired
//	private PostMapper postMapper;
//
//	//设置用户账号
//	@Test
//	void setUserTest()
//	{
//		User user = new User();
//		user.setUserAccount("张四adada");
//		user.setUserPassword("1234156");
//		user.setUserAvator("头像");
//		user.setUserName("昵称");
//		user.setUserProfile("简介");
//
//
//		//很玄学，数据库明明设置了字段为空设置默认值但是还是报该字段为空的错误,提示后端必须返回给数据库数据才可以
//		user.setUserRole("user");
//		user.setIsDelete(0);
//		user.setCreateTime(new Date());
//		user.setUpdateTime(new Date());
//		userMapper.setUsers(user);
//	}
//
//
//	//查询用户根据Id
//	@Test
//	void getUserById()
//	{
//		Long id = 1L;
//		User user = userMapper.getUserById(id);
//		System.out.println(user);
//	}
//
//
//
//	//爬虫爬取文章数据,采用调用他人接口方式
////	@Test
//	void getWritings()
//	{
//		String json = "{\"current\":1,\"pageSize\":8,\"sortField\":\"createTime\",\"sortOrder\":\"descend\",\"category\":\"文章\",\"reviewStatus\":1}";
//		String result = HttpRequest.post("https://www.code-nav.cn/api/post/search/page/vo")
//				.body(json)
//				.execute().body();
//
//		Map<String,Object> DataMap = JSONUtil.toBean(result, Map.class);
//
//		List<Post> postList = new ArrayList<>();
//		Integer code = (Integer) DataMap.get("code");
//		//响应成功
//		if(code == 0)
//		{
//			JSONObject data = (JSONObject) DataMap.get("data");
//			JSONArray records = (JSONArray) data.get("records");
//
//			System.out.println(records);
//
//			for(int i=0;i< records.size();i++)
//			{
//				JSONObject res = (JSONObject) records.get(i);
//				Post post = new Post();
//				post.setContent(res.getStr("content"));
//				post.setTitle(res.getStr("title"));
//
//				JSONArray tags = (JSONArray) res.get("tags");
//				String str = "";
//				for(int j=0;j< tags.size();j++) str+=tags.get(j)+",";
//				post.setTags(str);
//
//				post.setUserId(1L);
//
//				post.setUpdateTime(new Date());
//				post.setCreateTime(new Date());
//				postList.add(post);
//			}
//		}
//		System.out.println(postList);
//		for(int i=0;i<postList.size();i++)
//		{
//			boolean res = postMapper.AddOnePost(postList.get(i));
//			if(!res)
//				System.out.println("插入失败");
//			else
//				System.out.println("插入成功");
//		}
////		System.out.println(DataMap);
//	}
//
//	//实时获取图片,无须存储
//	@Test
//	//爬虫获取图片数据，采用获取element文档的元素信息
//	void getImages() throws IOException, NoSuchAlgorithmException, KeyManagementException {
//
//		// 创建一个信任所有证书的 SSL 上下文
//		TrustManager[] trustAllCertificates = new TrustManager[]{
//				new X509TrustManager() {
//					public X509Certificate[] getAcceptedIssuers() {
//						return null;
//					}
//					public void checkClientTrusted(X509Certificate[] certs, String authType) {
//					}
//					public void checkServerTrusted(X509Certificate[] certs, String authType) {
//					}
//				}
//		};
//		SSLContext sslContext = SSLContext.getInstance("TLS");
//		sslContext.init(null, trustAllCertificates, new SecureRandom());
//		HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//
//
//// 使用Jsoup进行请求
//		//与目标网页建立链接,获取HTML文档
//		Document document = Jsoup.connect("https://cn.bing.com/images/search?q=小黑子&form=HDRSC2&first=1").get();
////		System.out.println(document);
//		if(document == null)
//			log.error("document文档获取失败，所以图片无法正常加载,请检查相关设置");
//		//从HTML文档中搜索图片
//		Elements newsHeadlines = document.select(".iuscp.isv");
//		if(newsHeadlines == null)
//		log.error("HTML文档不存在图片文件，无法加载图片");
//		//遍历并提取图片和图片标题
//		List<Picture> pictureList = new ArrayList<>();
//		for(int i=0;i< newsHeadlines.size();i++)
//		{
//			//提取图片元素
//			Element element = newsHeadlines.get(i);
//			if(element == null)
//			log.error("图片元素获取失败");
//			//提取url
//			String e = element.select(".iusc").get(0).attr("m");
//			Map<String,Object> UrlMap = JSONUtil.toBean(e,Map.class);
//			String url = (String) UrlMap.get("murl");
//			if(url == null)
//				log.error("url提取失败");
//			//提取标题
//			String title = element.select(".inflnk").get(0).attr("aria-label");
//			if(title == null)
//				log.error("标题提取失败");
////			System.out.println(url);
////			System.out.println(title);
//			Picture picture = new Picture();
//			picture.setTitle(title);
//			picture.setUrl(url);
//			pictureList.add(picture);
//		}
//		System.out.println(pictureList);
//	}
//
//
//
//
//
//
//
//}
