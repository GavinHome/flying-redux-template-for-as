import 'package:flying_redux/flying_redux.dart';
import 'package:flutter/material.dart' hide Page, Action;

import 'action.dart';
import 'effect.dart';
import 'reducer.dart';
import 'state.dart';

class $namePage extends Page<$nameState, Map<String, dynamic>> {
  $namePage()
      : super(
          initState: initState,
          effect: buildEffect(),
          reducer: buildReducer(),
          dependencies: Dependencies<$nameState>(
              adapter: null, slots: <String, Dependent<$nameState>>{}),
          middleware: <Middleware<$nameState>>[],
          view: ($nameState state, Dispatch dispatch,
              ComponentContext<$nameState> ctx) {
            return Scaffold(
              body: Container(),
              floatingActionButton: FloatingActionButton(
                onPressed: () => dispatch($nameActionCreator.onSomeAction()),
                tooltip: 'some',
                child: const Icon(Icons.add),
              ),
            );
          },
        );
}
