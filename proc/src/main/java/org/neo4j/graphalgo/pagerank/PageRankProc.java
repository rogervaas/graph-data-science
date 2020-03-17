/*
 * Copyright (c) 2017-2020 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.graphalgo.pagerank;

import org.neo4j.graphalgo.AlgorithmFactory;
import org.neo4j.graphalgo.core.write.PropertyTranslator;

final class PageRankProc {

    static final String PAGE_RANK_DESCRIPTION =
        "Page Rank is an algorithm that measures the transitive influence or connectivity of nodes.";

    private PageRankProc() {}

    static <CONFIG extends PageRankBaseConfig> AlgorithmFactory<PageRank, CONFIG> algorithmFactory(CONFIG config) {
        if (config.relationshipWeightProperty() == null) {
            return new PageRankFactory<>();
        }
        return new PageRankFactory<>(PageRankAlgorithmType.WEIGHTED);
    }

    static final class ScoresTranslator implements PropertyTranslator.OfDouble<PageRank> {
        public static final ScoresTranslator INSTANCE = new ScoresTranslator();

        @Override
        public double toDouble(PageRank pageRank, long nodeId) {
            return pageRank.result().array().get(nodeId);
        }
    }
}
