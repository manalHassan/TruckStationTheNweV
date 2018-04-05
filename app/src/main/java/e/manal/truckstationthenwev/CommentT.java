package e.manal.truckstationthenwev;

/**
 * Created by hadeel on 3/25/18.
 */

public class CommentT {

        private String comment ;
        private String username;
        private String user_id;
        private String post_id;

        public CommentT(String comment, String username, String user_id , String post_id){
            this.comment=comment;
            this.username=username;
            this.user_id=user_id;
            this.post_id=post_id;

        }
    public CommentT(){
    }


        public void setComment(String comment) {
            this.comment = comment;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setPost_id(String post_id) {
            this.post_id = post_id;
        }


        public String getComment() {
            return comment;
        }

        public String getUsername() {
            return username;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getPost_id() { return post_id; }





}
