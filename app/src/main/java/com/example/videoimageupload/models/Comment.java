package com.example.videoimageupload.models;

public class Comment {
    private String userCommented;
    private String commentId; //will be  document name
    private String timeStamp;
    private String commentContent;
    private int noOfReports;
    private boolean isReply;
    private String replyId; //will be document name

    public Comment(String userCommented, String commentId, String timeStamp, String commentContent, int noOfReports, boolean isReply, String replyId) {
        this.userCommented = userCommented;
        this.commentId = commentId;
        this.timeStamp = timeStamp;
        this.commentContent = commentContent;
        this.noOfReports = noOfReports;
        this.isReply = isReply;
        this.replyId = replyId;
    }


    public String getUserCommented() {
        return userCommented;
    }

    public void setUserCommented(String userCommented) {
        this.userCommented = userCommented;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getNoOfReports() {
        return noOfReports;
    }

    public void setNoOfReports(int noOfReports) {
        this.noOfReports = noOfReports;
    }

    public boolean isReply() {
        return isReply;
    }

    public void setReply(boolean reply) {
        isReply = reply;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }
}
