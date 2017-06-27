
import com.funny.FunnyAnimalApplication;
import com.funny.model.domain.ImageObj;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by mac on 2017/6/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FunnyAnimalApplication.class)
public class FunnyAnimalApplicationTest {
    @Autowired
    MongoTemplate mongoTemplate;

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
}
