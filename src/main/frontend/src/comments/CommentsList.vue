<template>
    <table v-if="comments.length > 0">
        <thead>
        <tr>
            <th>Title</th>
            <th>Date</th>
            <th>Content</th>
            <th>Likes</th>
            <td></td>
        </tr>
        </thead>
        <tbody>
        <tr v-for="comment in comments" :key="comment.title">
            <td>{{ comment.title }}</td>
            <td>{{ comment.date }}</td>
            <td>{{ comment.description }}</td>
            <td>
                <ul v-if="comment.users">
                    <li v-for="user in comment.users" :key="user.login">
                        {{ user.login }}
                    </li>
                </ul>
            </td>
            <td style="text-align: right; min-width: 400px">
<!--                <button v-if="meeting.participants.indexOf(username) < 0" class="button-outline"-->
<!--                        @click="$emit('attend', meeting)">-->
<!--                    Zapisz się-->
<!--                </button>-->
<!--                <button v-else class="button-outline" @click="$emit('unattend', meeting)">Wypisz się</button>-->
<!--                <button v-if="meeting.participants.length === 0" class="button" @click="$emit('delete', meeting)">-->
                <button class="button" @click="$emit('delete', meeting)">
                    Delete post
                </button>
            </td>
        </tr>
        </tbody>
    </table>
</template>

<script>
    export default {
        props: ['comments', 'username'],
    }
</script>
