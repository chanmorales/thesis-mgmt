import { Author } from "../types/Authors";
import RequestHelper from "../helpers/RequestHelper";
import { PaginatedData } from "../types/Common";
import { RequestMethod } from "../common/constants";
import qs from "qs";

class AuthorService {
  resourcePath = "authors";

  async getAuthors(
    page: number,
    pageSize: number,
    query: string
  ): Promise<PaginatedData<Author>> {
    const queryParams = {
      page: page,
      pageSize: pageSize,
      query: query,
    };
    return await RequestHelper.doRemoteCallAsync(
      `${this.resourcePath}?${qs.stringify(queryParams)}`,
      "get authors"
    );
  }

  async createAuthor(author: Author): Promise<Author> {
    return await RequestHelper.doRemoteCallAsync(
      this.resourcePath,
      "create author",
      RequestMethod.POST,
      author
    );
  }

  async updateAuthor(authorId: number, author: Author): Promise<void> {
    return await RequestHelper.doRemoteCallAsync(
      `${this.resourcePath}/${authorId}`,
      "update author",
      RequestMethod.PUT,
      author
    );
  }

  async deleteAuthor(authorId: number): Promise<void> {
    return await RequestHelper.doRemoteCallAsync(
      `${this.resourcePath}/${authorId}`,
      "delete author",
      RequestMethod.DELETE
    );
  }
}

export default new AuthorService();
