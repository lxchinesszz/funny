
import com.funny.FunnyAnimalApplication;
import com.funny.model.dao.SayTableDao;
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
