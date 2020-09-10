<template>
  <div>
    <form @submit.prevent="addNewComment()" v-if="adding">
      <h3>Add new post</h3>
      <label>Title</label>
      <input type="text" v-model="newComment.title">
      <label>Content</label>
      <textarea v-model="newComment.description"></textarea>
      <label>Data</label>
      <input type="date" v-model="newComment.date">
      <button>Add</button>
      <span class="error" v-if="error">Post must include a title!</span>
    </form>
    <button @click="adding = true" v-else>Add new post</button>
  </div>
</template>

<script>
    export default {
        data() {
            return {
                newComment: {users: []},
                adding: false,
                error: false
            };
        },
        methods: {
            addNewComment() {
                this.error = false;
                if (this.newComment.title) {
                    this.$emit('added', this.newComment);
                    this.newComment = {users: []};
                    this.adding = false;
                } else {
                    this.error = true;
                }
            }
        }
    }
</script>

<style scoped>
  .error {
    color: #F00;
  }
</style>
