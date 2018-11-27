package shihoo.wang.coursedir.bean;

import java.io.Serializable;

/**
 * Created by shihoo.wang on 2018/11/16.
 * Email shihu.wang@bodyplus.cc
 */
public class CategoryBean implements Serializable {
    private String templateId ;
    private String templateName ;
    private String templateType ;
    private String templateSubName ;
    private String userId ;
    private String clubId ;
    private String image;
    private String difficulty;
    private String courseTime ;
    private String totalStep ;
    private String lastDatetime ;

    public String getLastDatetime() {
        return lastDatetime;
    }

    public void setLastDatetime(String lastDatetime) {
        this.lastDatetime = lastDatetime;
    }

    public String getTotalStep() {
        return totalStep;
    }

    public void setTotalStep(String totalStep) {
        this.totalStep = totalStep;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTemplateSubName() {
        return templateSubName;
    }

    public void setTemplateSubName(String templateSubName) {
        this.templateSubName = templateSubName;
    }
}
