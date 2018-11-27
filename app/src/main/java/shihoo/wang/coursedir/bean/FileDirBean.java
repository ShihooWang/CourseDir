package shihoo.wang.coursedir.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by shihoo.wang on 2018/11/7.
 * Email shihu.wang@bodyplus.cc
 */

@Entity
public class FileDirBean implements Serializable{

    private static final long serialVersionUID = 536871008L;
    /**
     *  @Entity 表示这个实体类一会会在数据库中生成对应的表，
        @Id 表示该字段是id，注意该字段的数据类型为包装类型Long
        @Property 则表示该属性将作为表的一个字段，其中nameInDb看名字就知道这个属性在数据库中对应的数据名称。
        @Transient 该注解表示这个属性将不会作为数据表中的一个字段。
        @NotNull 表示该字段不可以为空
        @Unique 表示该字段唯一。小伙伴们有兴趣可以自行研究。

        作者：当幸福来敲门58
        链接：https://www.jianshu.com/p/ec37ea99ef69
        來源：简书
        简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
     */

    @Id
    private Long id;

    /**
     *  parentFolder": "3",
     "  folderType": "2",
     "  folderName": "修改",
     "  parentFolder": "0",
     "  lastDatetime": "2018-11-19 19:05:14"

     "  templateId": "1206",
     "  templateName": "测试",
     "  templateSubName": "",
     "  templateType": "4",
     "  userId": "1066",
     "  clubId": "0",
     "  image": "http://test.app.bodyplus.cc/uploads/tpl/tpl_20180717021407.jpg",
     "  difficulty": "初级",
     "  courseTime": "1分钟"
     */

    @Property(nameInDb = "FOLDER_ID")
    String folderId;

    @Property(nameInDb = "FOLDER_TYPE")
    String folderType = "0"; // 0课程 非0为文件夹 类型： 1视频课，4私教课

    @Property(nameInDb = "FOLDER_NAME")
    String folderName = "";

    @Property(nameInDb = "DEPTH")
    String depth; // 文件夹层级深度

    @Property(nameInDb = "PARENT_FOLDER")
    String parentFolder = "0"; // 父级文件夹ID

    @Property(nameInDb = "LAST_DATETIME")
    String lastDatetime;

    /**
     * templateId": "1206",
     templateName": "测试",
     templateSubName": "",
     templateType": "4",
     userId": "1066",
     clubId": "0",
     image": "http://test.app.bodyplus.cc/uploads/tpl/tpl_20180717021407.jpg",
     difficulty": "初级",
     courseTime": "1分钟"
     */
    @Property(nameInDb = "TEMPLATE_ID")
    String templateId;
    @Property(nameInDb = "TEMPLATE_NAME")
    String templateName;
    @Property(nameInDb = "TEMPLATE_SUB_NAME")
    String templateSubName; // 课程(模板)副名称
    @Property(nameInDb = "TEMPLATE_TYPE")
    String templateType; // 课程类型 (1:本地视频 2:在线视频 3:教学课程 4:私教课程)
    @Property(nameInDb = "USER_ID")
    String userId;
    @Property(nameInDb = "CLUB_ID")
    String clubId;
    @Property(nameInDb = "IMAGE")
    String image;
    @Property(nameInDb = "DIFFICULTY")
    String difficulty;
    @Property(nameInDb = "COURSE_TIME")
    String courseTime;
    @Property(nameInDb = "TOTAL_STEP")
    String totalStep;


    @Generated(hash = 1462713311)
    public FileDirBean(Long id, String folderId, String folderType,
            String folderName, String depth, String parentFolder,
            String lastDatetime, String templateId, String templateName,
            String templateSubName, String templateType, String userId,
            String clubId, String image, String difficulty, String courseTime,
            String totalStep) {
        this.id = id;
        this.folderId = folderId;
        this.folderType = folderType;
        this.folderName = folderName;
        this.depth = depth;
        this.parentFolder = parentFolder;
        this.lastDatetime = lastDatetime;
        this.templateId = templateId;
        this.templateName = templateName;
        this.templateSubName = templateSubName;
        this.templateType = templateType;
        this.userId = userId;
        this.clubId = clubId;
        this.image = image;
        this.difficulty = difficulty;
        this.courseTime = courseTime;
        this.totalStep = totalStep;
    }

    @Generated(hash = 874359422)
    public FileDirBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolderId() {
        return this.folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getFolderType() {
        return this.folderType;
    }

    public void setFolderType(String folderType) {
        this.folderType = folderType;
    }

    public String getFolderName() {
        return this.folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getDepth() {
        return this.depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getParentFolder() {
        return this.parentFolder;
    }

    public void setParentFolder(String parentFolder) {
        this.parentFolder = parentFolder;
    }

    public String getLastDatetime() {
        return this.lastDatetime;
    }

    public void setLastDatetime(String lastDatetime) {
        this.lastDatetime = lastDatetime;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateSubName() {
        return this.templateSubName;
    }

    public void setTemplateSubName(String templateSubName) {
        this.templateSubName = templateSubName;
    }

    public String getTemplateType() {
        return this.templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClubId() {
        return this.clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCourseTime() {
        return this.courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getTotalStep() {
        return this.totalStep;
    }

    public void setTotalStep(String totalStep) {
        this.totalStep = totalStep;
    }

   


}
