
import com.funny.FunnyAnimalApplication;
import com.funny.model.domain.ImageObj;
import com.google.gson.Gson;
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
    public void testMongo(){
        System.out.println(mongoTemplate.getDb().getName());
        List<ImageObj> all = mongoTemplate.findAll(ImageObj.class);
        System.out.println(all.get(0).getText());
    }

    @Test
    public void insertMongo() {
        String url = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1498490936&di=81af350eb4ad63e53a203b65db3f1b5a&src=http://img5.duitang.com/uploads/item/201411/30/20141130235656_UuEFX.thumb.700_0.jpeg";
        String date = "2017/6/27";
        String author = "小安同学";
        String text = "时间很短，天涯很远。一山一水，一朝一夕。";

        ImageObj.ImageObjBuilder builder = ImageObj.builder();
        ImageObj build = builder.author(author).date(date).imgUrl(url).text(text).build();

        System.out.println(new Gson().toJson(build));
//        mongoTemplate.save(build);
    }

}
