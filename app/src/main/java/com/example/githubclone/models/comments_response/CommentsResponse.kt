package com.example.githubclone.models.comments_response

import com.google.gson.annotations.SerializedName

data class CommentsResponse(

    @field:SerializedName("CommentsResponse")
    val commentsResponse: List<CommentsResponseItem?>? = null
)

data class CommentsResponseItem(

    @field:SerializedName("author_association")
    val authorAssociation: String? = null,

    @field:SerializedName("issue_url")
    val issueUrl: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("performed_via_github_app")
    val performedViaGithubApp: Any? = null,

    @field:SerializedName("html_url")
    val htmlUrl: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("reactions")
    val reactions: Reactions? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("body")
    val body: String? = null,

    @field:SerializedName("user")
    val user: User? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("node_id")
    val nodeId: String? = null
)

data class Reactions(

    @field:SerializedName("confused")
    val confused: Int? = null,

    @field:SerializedName("-1")
    val jsonMember1: Int? = null,

    @field:SerializedName("total_count")
    val totalCount: Int? = null,

    @field:SerializedName("+1")
    val _jsonMember1: Int? = null,

    @field:SerializedName("rocket")
    val rocket: Int? = null,

    @field:SerializedName("hooray")
    val hooray: Int? = null,

    @field:SerializedName("eyes")
    val eyes: Int? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("laugh")
    val laugh: Int? = null,

    @field:SerializedName("heart")
    val heart: Int? = null
)

data class User(

    @field:SerializedName("gists_url")
    val gistsUrl: String? = null,

    @field:SerializedName("repos_url")
    val reposUrl: String? = null,

    @field:SerializedName("following_url")
    val followingUrl: String? = null,

    @field:SerializedName("starred_url")
    val starredUrl: String? = null,

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("followers_url")
    val followersUrl: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("subscriptions_url")
    val subscriptionsUrl: String? = null,

    @field:SerializedName("received_events_url")
    val receivedEventsUrl: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("events_url")
    val eventsUrl: String? = null,

    @field:SerializedName("html_url")
    val htmlUrl: String? = null,

    @field:SerializedName("site_admin")
    val siteAdmin: Boolean? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("gravatar_id")
    val gravatarId: String? = null,

    @field:SerializedName("node_id")
    val nodeId: String? = null,

    @field:SerializedName("organizations_url")
    val organizationsUrl: String? = null
)
