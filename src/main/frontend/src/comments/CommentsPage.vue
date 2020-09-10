<template>
    <div>
        <new-comment-form @added="addNewComment($event)"></new-comment-form>

        <span v-if="comments.length === 0">
               No posts
           </span>
        <h3 v-else>
            Posts ({{ comments.length }})
        </h3>

        <comments-list :comments="comments"
                       :username="username"
                       @like="addLike($event)"
                       @unlike="removeLike($event)"
                       @delete="removeComment($event)"></comments-list>
    </div>
</template>

<script>
    import NewCommentForm from "./NewCommentForm";
    import CommentsList from "./CommentsList";

    export default {
        components: {NewCommentForm, CommentsList},
        props: ['username'],
        data() {
            return {
                comments: []
            };
        },
        methods: {
            addNewComment(comment) {
                this.$http.post('comments', comment)
                    .then(response => this.comments.push(response.data));
            },
            addLike(comment) {
                this.$http.post(`comments/${comment.id}/likes`)
                    .then(response => this.comments.find(m => m.id === comment.id).users = response.data.users);
            },
            removeLike(comment) {
                this.$http.delete(`comments/${comment.id}/likes/unlike`)
                    .then(response => this.comments.find(m => m.id === comment.id).users = response.data.users);
            },
            removeComment(comment) {
                this.$http.delete(`meetings/${comment.id}`).then(() => this.comments = this.comments.filter(m => m.id !== comment.id));
            }
        },
        mounted() {
            this.$http.get('comments').then(response => {
                this.comments = response.data;
            });
        }
    }
</script>