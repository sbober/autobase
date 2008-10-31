git commit -a && \
git checkout trunk && \
git merge master && \
git commit -a && \
git svn dcommit && \
git checkout github/master && \
git merge master && \
git commit -a
