package com.daeliin.components.cms.article;

import com.daeliin.components.core.sql.BArticle;
import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.account.AccountService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ArticleService  {

    private final ArticleRepository repository;
    private final AccountService accountService;

    @Inject
    public ArticleService(ArticleRepository repository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    public Article findOne(String articleId) {
        BArticle bArticle = repository.findOne(articleId);

        if (bArticle == null) {
            return null;
        }

        Account author = accountService.findOne(bArticle.getAuthorId());

        return instantiate(bArticle, author.username);
    }

    public boolean exists(String articleId) {
        return repository.exists(articleId);
    }
//
//    public Article create(Article article) {
//        generateUrlFriendlyTitle(article);
//
//
//    }
//
//    public Iterable<Article> create(Iterable<Article> articles) {
//        articles.forEach(this::generateUrlFriendlyTitle);
//        return super.create(articles);
//    }
//
//    public Article update(Article article) {
//        generateUrlFriendlyTitle(article);
//        return super.update(id, article);
//    }
//
//    /**
//     * Publishes an article.
//     * @param articleId id of the article to publish
//     */
//    public void publish(String articleId) {
//        setInPublishedState(article);
//        super.update(article.getId(), article);
//    }
//
//    /**
//     * Publishes several articles.
//     * @param articles the articles to publish
//     */
//    public void publish(List<Article> articles) {
//        articles.forEach(this::setInPublishedState);
//        super.update(articles);
//    }
//
//    private void setInPublishedState(Article article) {
//        if (!article.isPublished()) {
//            article.setPublicationDate(LocalDateTime.now());
//            article.setPublished(true);
//        }
//    }
//
//    private void generateUrlFriendlyTitle(Article article) {
//        article.setUrlFriendlyTitle(new UrlFriendlyString(article.getTitle()).value());
//    }
//
    private Article instantiate(BArticle bArticle, String author) {
        return new Article(
                bArticle.getId(),
                bArticle.getCreationDate().toLocalDateTime(),
                author,
                bArticle.getTitle(),
                bArticle.getUrlFriendlyTitle(),
                bArticle.getDescription(),
                bArticle.getContent(),
                bArticle.getPublicationDate() != null ? bArticle.getPublicationDate().toLocalDateTime() : null,
                bArticle.getPublished());
    }
//
//    private Collection<Article> instantiate(Collection<BArticle> bArticles) {
//        Map<String, Account> accountByIds = new HashMap<>();
//        Set<String> authorIds = bArticles.stream().map(BArticle::getAuthorId).collect(toSet());
//
//        accountService.findAll(authorIds).forEach(account -> accountByIds.put(account.id(), account));
//
//        return bArticles
//                .stream()
//                .map(bNews -> instantiate(bNews, accountByIds.get(bNews.getAuthorId()).username))
//                .collect(toCollection(TreeSet::new));
//    }
}
