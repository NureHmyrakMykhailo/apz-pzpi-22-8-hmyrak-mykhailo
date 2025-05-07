using System;
using System.Collections.Generic;

// === Models ===
class User
{
    public string Username { get; }
    public List<Video> UploadedVideos { get; } = new List<Video>();

    public User(string username)
    {
        Username = username;
    }

    public void UploadVideo(Video video)
    {
        UploadedVideos.Add(video);
        Console.WriteLine($"{Username} uploaded: {video.Title}");
    }
}

class Video
{
    public string Title { get; }
    public User Uploader { get; }
    public List<Comment> Comments { get; } = new();
    public int Likes { get; private set; }

    public Video(string title, User uploader)
    {
        Title = title;
        Uploader = uploader;
    }

    public void AddComment(User user, string text)
    {
        Comments.Add(new Comment(user, text));
    }

    public void Like()
    {
        Likes++;
    }
}

class Comment
{
    public User Author { get; }
    public string Text { get; }

    public Comment(User author, string text)
    {
        Author = author;
        Text = text;
    }
}

// === Services ===
class RecommendationService
{
    public List<Video> RecommendVideos(User user, List<Video> allVideos)
    {
        // Простий алгоритм — повертає найпопулярніші
        return allVideos.OrderByDescending(v => v.Likes).Take(3).ToList();
    }
}

// === Program (UI Layer) ===
class Program
{
    static void Main()
    {
        var user1 = new User("Alice");
        var user2 = new User("Bob");

        var video1 = new Video("Funny Cats", user1);
        var video2 = new Video("How to Code", user2);
        var video3 = new Video("Top 10 Memes", user1);

        user1.UploadVideo(video1);
        user2.UploadVideo(video2);
        user1.UploadVideo(video3);

        video1.Like();
        video1.Like();
        video2.Like();

        video1.AddComment(user2, "Hilarious!");
        video2.AddComment(user1, "Very useful, thanks!");

        var recommendationService = new RecommendationService();
        var recommended = recommendationService.RecommendVideos(user2, new List<Video> { video1, video2, video3 });

        Console.WriteLine("\nRecommended for Bob:");
        foreach (var v in recommended)
        {
            Console.WriteLine($"- {v.Title} ({v.Likes} likes)");
        }
    }
}
