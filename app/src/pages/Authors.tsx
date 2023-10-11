import React, { useState } from "react";
import AuthorsTable from "../components/features/authors/AuthorsTable";
import AuthorConfigDialog from "../components/features/authors/AuthorConfigDialog";
import { Author } from "../types/Authors";
import AuthorService from "../services/AuthorService";
import NotificationHelper from "../helpers/NotificationHelper";

const Authors: React.FC = () => {
  const [activeAuthor, setActiveAuthor] = useState<Author>();
  const [isAuthorConfigDialogOpen, setIsAuthorConfigDialogOpen] =
    useState(false);
  const [refetchData, setRefetchData] = useState(false);

  const onAddAuthor = () => {
    setActiveAuthor(undefined);
    setIsAuthorConfigDialogOpen(true);
  };

  const onUpdateAuthor = (author: Author) => {
    setActiveAuthor(author);
    setIsAuthorConfigDialogOpen(true);
  };

  const onDeleteAuthor = async (authorId: number) => {
    try {
      await AuthorService.deleteAuthor(authorId);
      NotificationHelper.success("Author successfully deleted.");
    } finally {
      setRefetchData(true);
    }
  };

  const onCancel = () => {
    setIsAuthorConfigDialogOpen(false);
  };

  const onSubmit = async (author: Author, authorId: number) => {
    try {
      return authorId === -1
        ? await AuthorService.createAuthor(author)
        : await AuthorService.updateAuthor(authorId, author);
    } finally {
      NotificationHelper.success(
        `Author successfully ${authorId === -1 ? "created" : "updated"}.`
      );
      setIsAuthorConfigDialogOpen(false);
      setRefetchData(true);
    }
  };

  return (
    <div id="authors-container" className="h-full">
      <AuthorsTable
        refetchData={refetchData}
        setRefetchData={setRefetchData}
        onAddAuthor={onAddAuthor}
        onUpdateAuthor={onUpdateAuthor}
        onDeleteAuthor={onDeleteAuthor}
      />
      <AuthorConfigDialog
        open={isAuthorConfigDialogOpen}
        activeAuthor={activeAuthor}
        onCancel={onCancel}
        onSubmit={onSubmit}
      />
    </div>
  );
};

export default Authors;
