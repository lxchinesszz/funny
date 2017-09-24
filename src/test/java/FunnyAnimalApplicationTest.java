
import com.funny.FunnyAnimalApplication;
import com.funny.model.dao.SayTableDao;
import com.funny.model.domain.Banner;
import com.funny.model.domain.ImageObj;
import com.funny.model.domain.SayTable;
import com.funny.service.ImgEveryService;
import com.funny.util.PageUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.qiniu.common.Zone;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.security.auth.login.Configuration;
import javax.xml.ws.Response;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by mac on 2017/6/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FunnyAnimalApplication.class)
public class FunnyAnimalApplicationTest {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ImgEveryService imgEveryService;


    @Autowired
    SayTableDao sayTableDao;

    @Test
    public void getBan(){
        List<Banner> banner = imgEveryService.getBanner(new Date().getTime(), 4);
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(banner));
    }

    @Test
    public void saveBanner()throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date parse = simpleDateFormat.parse("20170924");
        Banner banner = Banner.builder().bannerText("测试banner").type("4").timestamp(parse.getTime()).bannerUrl("http://img.zcool.cn/community/012a6e5885aa8da801219c7766f63a.jpg@2o.jpg").build();
        imgEveryService.saveBanner(banner);


        Banner banner1 = Banner.builder().bannerText("测试banner1").type("4").timestamp(parse.getTime()).bannerUrl("http://img.zcool.cn/community/01240b58ef2863a8012049ef8c4170.png@1280w_1l_2o_100sh.png").build();
        imgEveryService.saveBanner(banner1);


        Banner banner2 = Banner.builder().bannerText("测试banner2").type("4").timestamp(parse.getTime()).bannerUrl("http://img.zcool.cn/community/012a6e5885aa8da801219c7766f63a.jpg@2o.jpg").build();
        imgEveryService.saveBanner(banner2);


        Banner banner3 = Banner.builder().bannerText("测试banner3").type("4").timestamp(parse.getTime()).bannerUrl("http://bpic.588ku.com/back_pic/05/12/03/84599593a431c81.jpg").build();
        imgEveryService.saveBanner(banner3);


        Banner banner4 = Banner.builder().bannerText("测试banner4").type("4").timestamp(parse.getTime()).bannerUrl("http://bpic.588ku.com/back_pic/03/65/44/8057ae8dfe9b121.jpg").build();
        imgEveryService.saveBanner(banner4);

        Banner banner5 = Banner.builder().bannerText("测试banner5").timestamp(parse.getTime()).bannerUrl("http://bpic.588ku.com/back_pic/02/67/58/81578e331cc7693.jpg").build();
        imgEveryService.saveBanner(banner5);
    }


    @Test
    public void addImage() {
        ImageObj imageObj = new ImageObj();
        imageObj.setText("老子真的明天不想上班");
        imageObj.setAuthor("薛之谦");
        imageObj.setDate("20170916");//yyyyMMdd
        imageObj.setType("4");
        imageObj.setImgUrl("http://img2.imgtn.bdimg.com/it/u=1891094618,1838349670&fm=214&gp=0.jpg");
        mongoTemplate.save(imageObj);

        ImageObj imageObj1 = new ImageObj();
        imageObj1.setText("凤兮凤兮归故乡,遨游四海求其凰");
        imageObj1.setAuthor("李白");
        imageObj1.setDate("20170916");//yyyyMMdd
        imageObj1.setType("4");
        imageObj1.setImgUrl("http://i0.hdslb.com/bfs/archive/6c05342cbe761079cd449ca7c075ae2900a4aff8.jpg");
        mongoTemplate.save(imageObj1);


        ImageObj imageObj2 = new ImageObj();
        imageObj2.setText("意志很犀利嘛，可惜比不过我的剑刃");
        imageObj2.setAuthor("露娜");
        imageObj2.setDate("20170916");//yyyyMMdd
        imageObj2.setType("4");
        imageObj2.setImgUrl("http://img2.yqdown.com/img2017/2/9/2017020931755773.jpg");
        mongoTemplate.save(imageObj2);

        ImageObj imageObj3 = new ImageObj();
        imageObj3.setText("以陛下的名义，你被捕了");
        imageObj3.setAuthor("狄仁杰");
        imageObj3.setDate("20170917");//yyyyMMdd
        imageObj3.setType("4");
        imageObj3.setImgUrl("http://i1.hdslb.com/bfs/archive/9de82be7f03d78f88eb26f247aa8dbbc7ef5fa33.jpg");
        mongoTemplate.save(imageObj3);


    }

    @Test
    public void page() {
        PageRequest pageRequest = PageUtils.buildPageRequest(1, 2);
        Page<SayTable> timestamp = sayTableDao.findAll(pageRequest);
        List<SayTable> content = timestamp.getContent();
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(content));
    }

    @Test
    public void testMongo() {
        System.out.println(mongoTemplate.getDb().getName());
        List<ImageObj> all = mongoTemplate.findAll(ImageObj.class);
        System.out.println(all.get(0).getText());
    }

    @Test
    public void insertMongo() {
        String url = "http://img.yanj.cn/store/goods/2093/2093_75db88665f8edbf6db1bb500c64a5dc9.jpg_max.jpg";
        String date = "2017/06/28";
        String author = "小安";
        String text = "驾驭命运的舵是奋斗。不抱有一丝幻想，不放弃一点机会，不停止一日努力。";
        ImageObj.ImageObjBuilder builder = ImageObj.builder();
        ImageObj build = builder.author(author).date(date).imgUrl(url).text(text).build();

        System.out.println(new Gson().toJson(build));
        mongoTemplate.save(build);
    }

    @Test
    public void handlerConnectionMongo() {
        MongoClient mongoClient = new MongoClient("47.94.100.125", 27017);
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("funnyanimal");
//        mongoDatabase.getCollection("funny_img");
        mongoDatabase.createCollection("funny_img");
        System.out.println(mongoDatabase.getCollection("funny_img").count());
    }

    @Test
    public void testToImageByToken() throws Exception {
        String ak = "3jws4LSQj3Nwi_bWktpNReSf2Rh4D4CU6rTcZlrA";
        String sk = "WrROm6H4tHqcQ5ZlosarRLIXn1OE8WcKv9XtSpTF";
        String bucket = "funny";
        long expireSeconds = 10;
        Auth auth = Auth.create(ak, sk);

        StringMap putPolicy = new StringMap();
        putPolicy.put("insertOnly", 0);
//        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        String ke = auth.uploadToken(bucket,
                "13162211551.png", expireSeconds, putPolicy);

        com.qiniu.storage.Configuration cfg = new com.qiniu.storage.Configuration(Zone.zone2());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);


//        file
        com.qiniu.http.Response response = uploadManager.put("/Users/liuxin/Downloads/红色球衣.jpeg", "13162211551.png", ke);
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        System.out.println(putRet.key);
        System.out.println(putRet.hash);
    }

}
